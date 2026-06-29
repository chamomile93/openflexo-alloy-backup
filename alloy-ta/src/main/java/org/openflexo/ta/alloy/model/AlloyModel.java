package org.openflexo.ta.alloy.model;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.parser.CompModule;
import edu.mit.csail.sdg.parser.CompUtil;
import org.openflexo.foundation.ontology.*;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.technologyadapter.FlexoModel;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.ta.alloy.metamodel.AlloyMetaModel;
import org.openflexo.ta.alloy.model.io.AlloyModelConverter;
import org.openflexo.ta.alloy.rm.AlloyModelResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Supposed to be an Alloy Model
 *
 * @author chamomile93
 */
public class AlloyModel extends FlexoOntologyObjectImpl<AlloyTechnologyAdapter> implements FlexoModel<AlloyModel, AlloyMetaModel>, IFlexoOntology<AlloyTechnologyAdapter> {
    protected static final Logger logger = Logger.getLogger(AlloyModel.class.getPackage().getName());
    protected AlloyModelResource alloyModelResource;
    // TODO might need to implement a meta model like "{@ AlloyMetaModel}"
    protected AlloyMetaModel alloyMetaModel;
    // TODO idf
    protected AlloyModelConverter converter;
    CompModule resource;
    boolean isLoaded;

    public AlloyModel(AlloyModelConverter converter, CompModule resource) {
        this.alloyMetaModel = null;
        this.converter = converter;
        this.resource = resource;
    }

    public AlloyModel(AlloyMetaModel alloyMetaModel, AlloyModelConverter converter, CompModule resource) {
        this.alloyMetaModel = alloyMetaModel;
        this.converter = converter;
        this.resource = resource;
    }

    public AlloyModel() {
        // TODO auto-generated constructor
    }

    //TODO the AlloyModelLibrary is needed ? since using OWLOntology as a base,
    // EMFModel seems to have termed equivalently with
    // TechnologyContextManager the supertype
    public static AlloyModel createEmptyAlloyModel() {
        AlloyModel returned = new AlloyModel();
        returned.resource = CompUtil.parseEverything_fromString(A4Reporter.NOP, "");
        returned.isLoaded = true;
        return returned;
    }

    /**
     * Name of Object.
     *
     * @return
     */
    @Override
    public String getName() {
        //TODO should get the name of the org.alloytools obj handled instead
        // of the Java signature
        return resource.getClass().getSimpleName();
    }

    /**
     * Sets name of object
     *
     * @param name
     * @throws Exception
     */
    @Override
    public void setName(String name) throws Exception {
        logger.log(Level.INFO, "AlloyModel.setName() called but nothing " + "happens");
    }

    /**
     * Meta Model.
     *
     * @return
     */
    @Override
    public AlloyMetaModel getMetaModel() {
        return alloyMetaModel;
    }

    /**
     * Uri of Object.
     *
     * @return
     */
    @Override
    public String getURI() {
        //TODO return the file path of the org.alloytools obj
        if (getResource() != null) {
            return getResource().getURI();
        }
        return resource.getClass().getSimpleName();
    }

    /**
     * @param objectURI
     * @return
     */
    @Override
    public Object getObject(String objectURI) {
        return getOntologyObject(objectURI);
    }

    /**
     * Description of Object.
     *
     * @return
     */
    @Override
    public String getDescription() {
        return "idk which description";
    }

    /**
     * @return
     */
    @Override
    public IFlexoOntology<AlloyTechnologyAdapter> getFlexoOntology() {
        return this;
    }

    /**
     * @return
     */
    @Override
    public String getDisplayableDescription() {
        return "idk which displayble description";
    }

    /**
     * Return the {@link AlloyTechnologyAdapter}
     *
     * @return
     */
    @Override
    public AlloyTechnologyAdapter getTechnologyAdapter() {
        return alloyMetaModel.getTechnologyAdapter();
    }

    /**
     * Version of Ontology.
     *
     * @return
     */
    @Override
    public String getVersion() {
        return "idk version";
    }

    /**
     * Ontologies imported by Ontology.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntology<AlloyTechnologyAdapter>> getImportedOntologies() {
        return Collections.singletonList((IFlexoOntology<AlloyTechnologyAdapter>) alloyMetaModel);
    }

    /**
     * Annotations upon Ontology.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyAnnotation> getAnnotations() {
        return Collections.emptyList();
    }

    /**
     * Return all classes accessible in the context of this ontology.<br>
     * This means that classes are also retrieved from imported ontologies (non-strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyClass<AlloyTechnologyAdapter>> getAccessibleClasses() {
        return getClasses();
    }

    /**
     * Return all individuals accessible in the context of this ontology.<br>
     * This means that individuals are also retrieved from imported ontologies (non-strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyIndividual<AlloyTechnologyAdapter>> getAccessibleIndividuals() {
        return getIndividuals();
    }

    /**
     * Return all object properties accessible in the context of this ontology.<br>
     * This means that properties are also retrieved from imported ontologies (non-strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyObjectProperty<AlloyTechnologyAdapter>> getAccessibleObjectProperties() {
        return getObjectProperties();
    }

    /**
     * Return all data properties accessible in the context of this ontology.<br>
     * This means that properties are also retrieved from imported ontologies (non-strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyDataProperty<AlloyTechnologyAdapter>> getAccessibleDataProperties() {
        return getDataProperties();
    }

    /**
     * Retrieve an ontology object from its URI, in the strict context of this ontology. That means that only objects declared in this
     * ontology are subject to look up. If searched object is declared in an imported ontology for example, this method will not find it and
     * will return null. Use {@link #getOntologyObject(String)} instead.
     *
     * @param objectURI
     * @return
     */
    @Override
    public IFlexoOntologyConcept<AlloyTechnologyAdapter> getDeclaredOntologyObject(String objectURI) {
        return getDeclaredOntologyObject(objectURI);
    }

    /**
     * Retrieve an class from its URI, in the strict context of this ontology. That means that only objects declared in this ontology are
     * subject to look up. If searched object is declared in an imported ontology for example, this method will not find it and will return
     * null. Use {@link #getClass()} instead.
     *
     * @param classURI@return
     */
    @Override
    public IFlexoOntologyClass<AlloyTechnologyAdapter> getDeclaredClass(String classURI) {
        return getClass(classURI);
    }

    /**
     * Retrieve an individual from its URI, in the strict context of this ontology. That means that only objects declared in this ontology
     * are subject to look up. If searched object is declared in an imported ontology for example, this method will not find it and will return null.
     *
     * @param individualURI@return
     */
    @Override
    public IFlexoOntologyIndividual<AlloyTechnologyAdapter> getDeclaredIndividual(String individualURI) {
        return getIndividual(individualURI);
    }

    /**
     * Retrieve an object property from its URI, in the strict context of this ontology. That means that only objects declared in this
     * ontology are subject to look up. If searched object is declared in an imported ontology for example, this method will not find it and will return null.
     *
     * @param propertyURI@return
     */
    @Override
    public IFlexoOntologyObjectProperty<AlloyTechnologyAdapter> getDeclaredObjectProperty(String propertyURI) {
        return getObjectProperty(propertyURI);
    }

    /**
     * Retrieve an datatype property from its URI, in the strict context of this ontology. That means that only objects declared in this
     * ontology are subject to look up. If searched object is declared in an imported ontology for example, this method will not find it and will return null.
     *
     * @param propertyURI@return
     */
    @Override
    public IFlexoOntologyDataProperty<AlloyTechnologyAdapter> getDeclaredDataProperty(String propertyURI) {
        return getDeclaredDataProperty(propertyURI);
    }

    /**
     * Retrieve a property from its URI, in the strict context of this ontology. That means that only objects declared in this ontology are
     * subject to look up. If searched object is declared in an imported
     * ontology for example, this method will not find it and will return  null.
     *
     * @param objectURI
     * @return
     */
    @Override
    public IFlexoOntologyStructuralProperty<AlloyTechnologyAdapter> getDeclaredProperty(String objectURI) {
        return getProperty(objectURI);
    }

    /**
     * Return the root concept accessible from the scope defined by this ontology (for example in OWL technology this is the owl:Thing
     * concept, in Java this is java.lang.object, etc...)
     *
     * @return
     */
    @Override
    public IFlexoOntologyClass<AlloyTechnologyAdapter> getRootConcept() {
        return alloyMetaModel.getRootConcept();
    }

    /**
     * Sub container of container.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyContainer<AlloyTechnologyAdapter>> getSubContainers() {
        return Collections.emptyList();
    }

    /**
     * Concepts defined by Ontology.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyConcept<AlloyTechnologyAdapter>> getConcepts() {
        List<IFlexoOntologyConcept<AlloyTechnologyAdapter>> concepts = new ArrayList<>();
        concepts.addAll(getIndividuals());
        return Collections.unmodifiableList(concepts);
    }

    /**
     * DataTypes defined by Ontology.
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyDataType<AlloyTechnologyAdapter>> getDataTypes() {
        return Collections.emptyList();
    }

    /**
     * Retrieve an ontology object from its URI, in the context of this container (if this container is an ontology, will lookup in ontology
     * and recursively on imported ontologies)<br>
     * The current container defines the scope, in which to lookup returned object. This method does NOT try to lookup object from outer
     * scope ontologies.
     *
     * @param objectNameOrURI@return
     */
    @Override
    public IFlexoOntologyConcept<AlloyTechnologyAdapter> getOntologyObject(String objectNameOrURI) {
        IFlexoOntologyConcept<AlloyTechnologyAdapter> result = null;
        for (IFlexoOntologyConcept<AlloyTechnologyAdapter> concept : getConcepts()) {
            if (concept.getURI().equalsIgnoreCase(objectNameOrURI)) {
                result = concept;
            }
        }
        return result;
    }

    /**
     * Retrieve an class from its URI or name, in the context of this container (if this container is an ontology, will lookup in ontology
     * and recursively on imported ontologies)<br>
     * The current container defines the scope, in which to lookup returned object. This method does NOT try to lookup object from outer
     * scope ontologies.
     *
     * @param classNameOrURI@return
     */
    @Override
    public IFlexoOntologyClass<AlloyTechnologyAdapter> getClass(String classNameOrURI) {
        return null;
    }

    /**
     * Retrieve an individual from its URI, in the context of this container (if this container is an ontology, will lookup in ontology and
     * recursively on imported ontologies)<br>
     * The current container defines the scope, in which to lookup returned object. This method does NOT try to lookup object from outer
     * scope ontologies.
     *
     * @param individualURI@return
     */
    @Override
    public IFlexoOntologyIndividual<AlloyTechnologyAdapter> getIndividual(String individualURI) {
        //TODO this is incorrect
        Object result = null;
        for (Object individual : getIndividuals()) {
            if (individual.getClass().getSimpleName().equals(individualURI)) {
                result = individual;
            }
        }
        return null;
    }

    /**
     * Retrieve an object property from its URI, in the context of this container (if this container is an ontology, will lookup in ontology
     * and recursively on imported ontologies)<br>
     * The current container defines the scope, in which to lookup returned object. This method does NOT try to lookup object from outer
     * scope ontologies.
     *
     * @param propertyURI@return
     */
    @Override
    public IFlexoOntologyObjectProperty<AlloyTechnologyAdapter> getObjectProperty(String propertyURI) {
        return null;
    }

    /**
     * Retrieve an datatype property from its URI, in the context of this container (if this container is an ontology, will lookup in
     * ontology and recursively on imported ontologies)<br>
     * The current container defines the scope, in which to lookup returned object. This method does NOT try to lookup object from outer
     * scope ontologies.
     *
     * @param propertyURI@return
     */
    @Override
    public IFlexoOntologyDataProperty<AlloyTechnologyAdapter> getDataProperty(String propertyURI) {
        return null;
    }

    /**
     * Retrieve a property from its URI, in the context of this container (if this container is an ontology, will lookup in ontology and
     * recursively on imported ontologies)<br>
     * The current container defines the scope, in which to lookup returned object. This method does NOT try to lookup object from outer
     * scope ontologies.
     *
     * @param objectURI
     * @return
     */
    @Override
    public IFlexoOntologyStructuralProperty<AlloyTechnologyAdapter> getProperty(String objectURI) {
        IFlexoOntologyStructuralProperty<AlloyTechnologyAdapter> result = getDataProperty(objectURI);
        if (result == null) {
            result = getObjectProperty(objectURI);
        }
        return result;
    }

    /**
     * Return all classes explicitely defined in this container (strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyClass<AlloyTechnologyAdapter>> getClasses() {
        return Collections.emptyList();
    }

    /**
     * Return all individuals explicitely defined in this container (strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyIndividual<AlloyTechnologyAdapter>> getIndividuals() {
        List<IFlexoOntologyIndividual<AlloyTechnologyAdapter>> result = new ArrayList<>();
        // TODO seems needed to "get" "data" from "source files"
        //result.addAll(converter.getIndividuals().values());
        return Collections.unmodifiableList(result);
    }

    /**
     * Return all datatype properties explicitely defined in this container (strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyDataProperty<AlloyTechnologyAdapter>> getDataProperties() {
        return Collections.emptyList();
    }

    /**
     * Return all object properties explicitely defined in this container (strict mode)
     *
     * @return
     */
    @Override
    public List<? extends IFlexoOntologyObjectProperty<AlloyTechnologyAdapter>> getObjectProperties() {
        return Collections.emptyList();
    }

    /**
     * @return
     */
    @Override
    public FlexoResource<AlloyModel> getResource() {
        return alloyModelResource;
    }

    /**
     * @param resource
     */
    @Override
    public void setResource(FlexoResource<AlloyModel> resource) {
        alloyModelResource = (AlloyModelResource) resource;
    }
}