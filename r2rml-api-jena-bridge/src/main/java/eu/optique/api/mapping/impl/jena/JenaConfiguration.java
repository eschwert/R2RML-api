package eu.optique.api.mapping.impl.jena;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.R2RMLVocabulary;

/**
 * The library configuration for the Jena API.
 * 
 * Uses org.apache.jena.rdf.model.Resource as the resource class,
 * org.apache.jena.rdf.model.Statement as the triple class and
 * org.apache.jena.rdf.model.Model as the graph class.
 * 
 * @author Marius Strandhaug
 */
public class JenaConfiguration implements LibConfiguration {

	private Class<Resource> res = Resource.class;
	private Class<Statement> trpl = Statement.class;
	private Class<Model> graph = Model.class;

	@Override
	public Resource createResource(String URI) {

		return org.apache.jena.rdf.model.ResourceFactory.createResource(URI);

	}

	@Override
	public Resource createBNode() {

		return org.apache.jena.rdf.model.ResourceFactory.createResource();

	}

	@Override
	public Statement createTriple(Object subject, Object predicate,
			Object object) {
		Resource s = (Resource) subject;
		Property p = org.apache.jena.rdf.model.ResourceFactory
				.createProperty(((Resource) predicate).getURI());
		Resource o = (Resource) object;

		return org.apache.jena.rdf.model.ResourceFactory.createStatement(s, p,
				o);

	}

	@Override
	public Statement createLiteralTriple(Object subject, Object predicate,
			String litObject) {

		Property p = org.apache.jena.rdf.model.ResourceFactory
				.createProperty(((Resource) predicate).getURI());
		Literal l = org.apache.jena.rdf.model.ResourceFactory
				.createPlainLiteral(litObject);
		return org.apache.jena.rdf.model.ResourceFactory.createStatement(
				(Resource) subject, p, l);

	}

	@Override
	public Object createGraph(Collection<TriplesMap> maps) {

		Model m = ModelFactory.createDefaultModel();
		m.setNsPrefix("rr", R2RMLVocabulary.NAMESPACE);

		for (TriplesMap tm : maps) {
			Set<Statement> stmts = tm.serialize(Statement.class);

			for (Statement st : stmts)
				m.add(st);
		}

		return m;

	}

	@Override
	public Resource getRDFType() {
		return RDF.type;
	}

	@Override
	public Collection<Object> getSubjects(Object graph, Object pred, Object obj) {

		Model m = (Model) graph;
		Property p = org.apache.jena.rdf.model.ResourceFactory
				.createProperty(((Resource) pred).getURI());

		Collection<Object> c = new HashSet<Object>();

		StmtIterator it = m.listStatements(null, p, (RDFNode) obj);
		while (it.hasNext()) {
			c.add(it.nextStatement().getSubject());
		}

		return c;
	}

	@Override
	public Collection<Object> getObjects(Object graph, Object subj, Object pred) {

		Model m = (Model) graph;

		Property p = org.apache.jena.rdf.model.ResourceFactory
				.createProperty(((Resource) pred).getURI());

		Collection<Object> c = new HashSet<Object>();

		StmtIterator it = m.listStatements((Resource) subj, p, (RDFNode) null);
		while (it.hasNext()) {
			c.add(it.nextStatement().getObject());
		}

		return c;

	}

	@Override
	public Class<Resource> getResourceClass() {
		return res;
	}

	@Override
	public Class<Statement> getTripleClass() {
		return trpl;
	}

	@Override
	public Class<Model> getGraphClass() {
		return graph;
	}

    @Override
    public String getLexicalForm(Object node) {
        if(node instanceof Literal){
            return ((Literal) node).getLexicalForm();
        } else {
            return node.toString();
        }
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		result = prime * result + ((trpl == null) ? 0 : trpl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof JenaConfiguration)) {
			return false;
		}
		JenaConfiguration other = (JenaConfiguration) obj;
		if (res == null) {
			if (other.res != null) {
				return false;
			}
		} else if (!res.equals(other.res)) {
			return false;
		}
		if (trpl == null) {
			if (other.trpl != null) {
				return false;
			}
		} else if (!trpl.equals(other.trpl)) {
			return false;
		}
		return true;
	}

}
