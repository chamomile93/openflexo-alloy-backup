package org.openflexo.ta.alloy;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.ta.alloy.rm.AlloyMetaModelResource;
import org.openflexo.ta.alloy.rm.AlloyModelResource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * idk if it's necessary
 *
 * @author chamomile93
 *
 */
public class AlloyTechnologyContextManager extends TechnologyContextManager<AlloyTechnologyAdapter> {

    protected static final Logger logger = Logger.getLogger(AlloyTechnologyContextManager.class.getPackage().getName());

    protected Map<String, AlloyModelResource> models = new HashMap<>();
    /**
     * Stores all known metamodels where key is the URI of metamodel
     */
    protected Map<String, AlloyMetaModelResource> metamodels = new HashMap<>();

    public AlloyTechnologyContextManager(AlloyTechnologyAdapter adapter, FlexoResourceCenterService resourceCenterService) {
        super(adapter, resourceCenterService);
        // TODO Auto-generated constructor stub
    }

    public AlloyModelResource getModel(File modelFile) {
        return models.get(modelFile);
    }

    public AlloyMetaModelResource getMetaModel(File modelFile) {
        return metamodels.get(modelFile);
    }

    /**
     * Called when a new meta model was registered, notify the {@link TechnologyContextManager}
     *
     */
    public void registerMetaModel(AlloyMetaModelResource newMetaModelResource) {
        String mmURI = newMetaModelResource.getURI();
        AlloyMetaModelResource existingMM = metamodels.get(mmURI);
        if (existingMM == null) {
            registerResource(newMetaModelResource);
            metamodels.put(mmURI, newMetaModelResource);
        } else {
            logger.warning(" There already exists a MM with that URI => I will not register this one!");
        }
    }

    /**
     * Called when a new model is registered, notify the {@link TechnologyContextManager}
     *
     * @param newModelResource
     */
    public void registerModel(AlloyModelResource newModelResource) {
        registerResource(newModelResource);
        models.put(newModelResource.getURI(), newModelResource);
    }

    public void newMetaModelWasRegistered(AlloyMetaModelResource mmResource, FlexoResourceCenter<?> resourceCenter) {
        // We iterate on all AlloyModelResource which does not declare any metamodel
        for (AlloyModelResource alloyModelResource : models.values()) {
            if (alloyModelResource.getMetaModelResource() == null) {
                if (alloyModelResource.getMetaModelResource().getURI() != null && alloyModelResource.getMetaModelResource().getURI().equals(mmResource.getURI())) {
                    alloyModelResource.setMetaModelResource(mmResource);
                }
            }
        }
    }
}