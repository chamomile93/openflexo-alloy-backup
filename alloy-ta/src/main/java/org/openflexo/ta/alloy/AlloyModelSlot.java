/**
 *
 * Copyright (c) 2013-2015, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
 * <p>
 * This file is part of Emfconnector, a component of the software infrastructure
 * developed at Openflexo.
 * <p>
 * <p>
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either
 * version 1.1 of the License, or any later version ), which is available at
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * <p>
 * You can redistribute it and/or modify under the terms of either of these licenses
 * <p>
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 * <p>
 * Additional permission under GNU GPL version 3 section 7
 * <p>
 * If you modify this Program, or any covered work, by linking or
 * combining it with software containing parts covered by the terms
 * of EPL 1.0, the licensors of this Program grant you additional permission
 * to convey the resulting work. *
 * <p>
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p>
 * See http://www.openflexo.org/license.html for details.
 * <p>
 * <p>
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 *
 */

package org.openflexo.ta.alloy;

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.DeclareActorReferences;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.annotations.FMLAttribute;
import org.openflexo.foundation.ontology.IFlexoOntologyObject;
import org.openflexo.foundation.ontology.fml.rt.ConceptActorReference;
import org.openflexo.foundation.ontology.fml.rt.FlexoOntologyModelSlotInstance;
import org.openflexo.foundation.ontology.technologyadapter.FlexoOntologyModelSlot;
import org.openflexo.foundation.resource.FileSystemBasedResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.technologyadapter.FlexoMetaModelResource;
import org.openflexo.pamela.annotations.*;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.ta.alloy.metamodel.AlloyMetaModel;
import org.openflexo.ta.alloy.model.AlloyModel;
import org.openflexo.ta.alloy.rm.AlloyMetaModelResource;
import org.openflexo.ta.alloy.rm.AlloyModelResource;
import org.openflexo.ta.alloy.rm.AlloyModelResourceFactory;

import java.io.File;
import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * Implementation of the ModelSlot class for the EMF technology adapter<br>
 * We expect here to connect an EMF model conform to an AlloyMetaModel
 *
 * @author sylvain
 *
 */
//@DeclareFlexoRoles({ EMFObjectIndividualRole.class, EMFClassClassRole.class,
//		EMFEnumClassRole.class })
//@DeclareEditionActions({ CreateAlloyModel.class, DuplicateAlloyModel.class,
// AddEMFObjectIndividual.class })
//@DeclareFetchRequests({ SelectEMFObjectIndividual.class, SelectUniqueEMFObjectIndividual.class })
@DeclareActorReferences({ConceptActorReference.class, FlexoOntologyModelSlotInstance.class})
@ModelEntity
@ImplementationClass(AlloyModelSlot.AlloyModelSlotImpl.class)
@XMLElement
@FML("AlloyModelSlot")
public interface AlloyModelSlot extends FlexoOntologyModelSlot<AlloyModel, AlloyMetaModel, AlloyModelResource, AlloyTechnologyAdapter> {

    @PropertyIdentifier(type = AlloyMetaModel.class)
    String META_MODEL_KEY = "metaModel";

    @Override
    AlloyTechnologyAdapter getModelSlotTechnologyAdapter();

    @Getter(value = META_MODEL_KEY, ignoreType = true)
    @FMLAttribute(value = META_MODEL_KEY, required = true)
    AlloyMetaModel getMetaModel();

    @Setter(META_MODEL_KEY)
    void setMetaModel(AlloyMetaModel aMetaModel);

    abstract class AlloyModelSlotImpl extends FlexoOntologyModelSlotImpl<AlloyModel, AlloyMetaModel, AlloyModelResource, AlloyTechnologyAdapter> implements AlloyModelSlot {

        private static final Logger logger = Logger.getLogger(AlloyModelSlot.class.getPackage().getName());

        @Override
        public Class<AlloyTechnologyAdapter> getTechnologyAdapterClass() {
            return AlloyTechnologyAdapter.class;
        }

        @Override
        public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
//			if (EMFObjectIndividualRole.class.isAssignableFrom(patternRoleClass)) {
//				return "individual";
//			}
            return null;
        }

        @Override
        public String getURIForObject(AlloyModel model, Object o) {
            if (o instanceof IFlexoOntologyObject) {
                return ((IFlexoOntologyObject) o).getURI();
            }
            return null;
        }

        @Override
        public Object retrieveObjectWithURI(AlloyModel model, String objectURI) {
            if (model != null) {
                return model.getObject(objectURI);
            }
            return null;
        }

        @Override
        public Type getType() {
            return AlloyModel.class;
        }

        @Override
        public String getTypeDescription() {
            return "EMF Model";
        }

        @Override
        public AlloyTechnologyAdapter getModelSlotTechnologyAdapter() {
            return (AlloyTechnologyAdapter) super.getModelSlotTechnologyAdapter();
        }

        @Override
        public AlloyModelResource createProjectSpecificEmptyModel(FlexoResourceCenter<?> rc, String filename, String relativePath, String modelUri, FlexoMetaModelResource<AlloyModel, AlloyMetaModel, ?> metaModelResource) {

            AlloyTechnologyAdapter alloyTechnologyAdapter = getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(AlloyTechnologyAdapter.class);
            AlloyModelResourceFactory factory = getModelSlotTechnologyAdapter().getAlloyResourceFactory();

            Object serializationArtefact = null;
            if (metaModelResource instanceof AlloyMetaModelResource) {
                serializationArtefact = alloyTechnologyAdapter.retrieveResourceSerializationArtefact(rc, filename, relativePath, ((AlloyMetaModelResource) metaModelResource).getModelFileExtension());
            } else {
                logger.warning("Not implemented : createProjectSpecificEmptyModel for " + metaModelResource);
            }

            AlloyModelResource newAlloyModelResource;
            try {
                newAlloyModelResource = factory.makeResource(serializationArtefact, (FlexoResourceCenter) rc, filename, modelUri, true);
                newAlloyModelResource.setMetaModelResource((FlexoMetaModelResource) metaModelResource);
                return newAlloyModelResource;
            } catch (SaveResourceException e) {
                e.printStackTrace();
            } catch (ModelDefinitionException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public AlloyModelResource createSharedEmptyModel(FlexoResourceCenter<?> resourceCenter, String relativePath, String filename, String modelUri, FlexoMetaModelResource<AlloyModel, AlloyMetaModel, ?> metaModelResource) {

            if (resourceCenter instanceof FileSystemBasedResourceCenter) {
                File modelDirectory = new File(((FileSystemBasedResourceCenter) resourceCenter).getRootDirectory(), relativePath);
                File modelFile = new File(modelDirectory, filename);
                try {
                    return getModelSlotTechnologyAdapter().getAlloyResourceFactory().makeResource(modelFile, (FlexoResourceCenter<File>) resourceCenter, true);
                } catch (SaveResourceException e) {
                    e.printStackTrace();
                } catch (ModelDefinitionException e) {
                    e.printStackTrace();
                }
            }

            return null;

            // return getModelSlotTechnologyAdapter().createNewAlloyModel(
            // (FileSystemBasedResourceCenter) resourceCenter, relativePath,
            // filename, modelUri, (AlloyMetaModelResource) metaModelResource);
        }

        @Override
        public boolean isStrictMetaModelling() {
            return true;
        }

        @Override
        public AlloyMetaModel getMetaModel() {
            if (getMetaModelResource() != null) {
                return getMetaModelResource().getMetaModelData();
            }
            return null;
        }

        @Override
        public void setMetaModel(AlloyMetaModel aMetaModel) {
            setMetaModelResource(aMetaModel != null ? aMetaModel.getResource() : null);
        }

    }
}
