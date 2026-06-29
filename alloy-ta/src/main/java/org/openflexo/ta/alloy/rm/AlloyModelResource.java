package org.openflexo.ta.alloy.rm;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.*;
import org.openflexo.foundation.technologyadapter.FlexoMetaModelResource;
import org.openflexo.foundation.technologyadapter.FlexoModelResource;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResource;
import org.openflexo.pamela.annotations.Getter;
import org.openflexo.pamela.annotations.ImplementationClass;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.annotations.Setter;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.ta.alloy.AlloyTechnologyContextManager;
import org.openflexo.ta.alloy.metamodel.AlloyMetaModel;
import org.openflexo.ta.alloy.model.AlloyModel;
import org.openflexo.ta.alloy.model.io.AlloyModelConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Supposed to be a model of an Alloy Resource
 *
 * @author chamomile93
 *
 */
@ModelEntity
@ImplementationClass(AlloyModelResource.AlloyModelResourceImpl.class)
public interface AlloyModelResource extends FlexoModelResource<AlloyModel, AlloyMetaModel, AlloyTechnologyAdapter, AlloyTechnologyAdapter>, TechnologyAdapterResource<AlloyModel, AlloyTechnologyAdapter> {

    String ALLOY_MODEL_RESOURCE = "alloyModelResource";
    String TECHNOLOGY_CONTEXT_MANAGER = "technologyContextManager";
    String ALS_EXTENSION = AlloyModelResourceFactory.ALS_FILE_EXTENSION;

    @Getter(value = ALLOY_MODEL_RESOURCE)
    AlloyModelResource getAlloyModelResource();

    @Override
    @Getter(value = TECHNOLOGY_CONTEXT_MANAGER, ignoreType = true)
    AlloyTechnologyContextManager getTechnologyContextManager();

    @Setter(TECHNOLOGY_CONTEXT_MANAGER)
    void setTechnologyContextManager(AlloyTechnologyContextManager technologyContextManager);

    abstract class AlloyModelResourceImpl extends FlexoResourceImpl<AlloyModel> implements AlloyModelResource {

        private static final Logger logger = Logger.getLogger(AlloyModelResource.class.getPackage().getName());

        /**
         * Load the &quot;real&quot; load resource data of this resource.
         *
         * @return supposed to be the resource data.
         */
        @Override
        public AlloyModel loadResourceData() {
            AlloyModelConverter converter = new AlloyModelConverter();
            AlloyModel resourceData;
            //TODO do we need a metamodel ?
//			FlexoMetaModelResource<AlloyModel, AlloyMetaModel, AlloyTechnologyAdapter> metaModelResource = getMetaModelResource();
//			AlloyMetaModel metaModelData = metaModelResource.getMetaModelData();
            AlloyModelResource alloyModelResource = getAlloyModelResource();
            resourceData = converter.convertModel(alloyModelResource);
            setResourceData(resourceData);
            resourceData.setResource(this);
            return resourceData;
        }

        @Override
        public FlexoMetaModelResource<AlloyModel, AlloyMetaModel, AlloyTechnologyAdapter> getMetaModelResource() {
            logger.warning("FlexoMetaModelResource() not fully implemented in" + " AlloyModelResource");
            // TODO: implement this and extends cardinality

            return getTechnologyContextManager().getMetaModel(new File(getAlloyModelResource().getURI()));
        }

        /**
         * Save the &quot;real&quot; resource data of this resource.
         *
         * @throws SaveResourceException
         */
        @Override
        public void save() throws SaveResourceException {
            AlloyModel resourceData;
            try {
                resourceData = getResourceData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new SaveResourceException(getIODelegate());
            } catch (ResourceLoadingCancelledException e) {
                e.printStackTrace();
                throw new SaveResourceException(getIODelegate());
            } catch (FlexoException e) {
                e.printStackTrace();
                throw new SaveResourceException(getIODelegate());
            }

            if (!getIODelegate().hasWritePermission()) {
                if (logger.isLoggable(Level.WARNING)) {
                    // logger.warning("Permission denied : " + getFile().getAbsolutePath());
                    logger.warning("Permission denied : " + getIODelegate().toString());
                }
                throw new SaveResourcePermissionDeniedException(getIODelegate());
            }
            if (resourceData != null) {
                FileWritingLock lock = getIODelegate().willWriteOnDisk();
                writeToFile();
                getIODelegate().hasWrittenOnDisk(lock);
                notifyResourceStatusChanged();
                resourceData.clearIsModified(false);
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("Succeeding to save Resource " + getURI() + " : " + getIODelegate().toString());
                }
            }
        }

        @Override
        public AlloyModel getModelData() {
            try {
                return getResourceData();
            } catch (ResourceLoadingCancelledException e) {
                e.printStackTrace();
                return null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (FlexoException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public AlloyModel getModel() {
            return getModelData();
        }

        /**
         * Write file.
         *
         * @throws SaveResourceException
         */
        private void writeToFile() throws SaveResourceException {
            getAlloyModelResource().save();
            logger.info("Wrote " + getIODelegate().toString());
        }

        @Override
        public Class<AlloyModel> getResourceDataClass() {
            return AlloyModel.class;
        }

        @Override
        public AlloyTechnologyContextManager getTechnologyContextManager() {
            return (AlloyTechnologyContextManager) performSuperGetter(TECHNOLOGY_CONTEXT_MANAGER);
        }

    }

}
