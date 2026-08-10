/**
 *
 * Copyright (c) 2013-2014, Openflexo
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

package org.openflexo.ta.alloy.rm;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.InnerResourceData;
import org.openflexo.foundation.ontology.IFlexoOntologyConcept;
import org.openflexo.foundation.resource.FlexoIODelegate;
import org.openflexo.foundation.resource.FlexoResourceImpl;
import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.technologyadapter.FlexoMetaModelResource;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResource;
import org.openflexo.pamela.annotations.Getter;
import org.openflexo.pamela.annotations.ModelEntity;
import org.openflexo.pamela.annotations.Setter;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.ta.alloy.AlloyTechnologyContextManager;
import org.openflexo.ta.alloy.metamodel.AlloyMetaModel;
import org.openflexo.ta.alloy.model.AlloyModel;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

/**
 * Represents an abstract Alloy metamodel resource
 *
 * @author chamomile93
 *
 */
@ModelEntity(isAbstract = true)
public interface AlloyMetaModelResource extends FlexoMetaModelResource<AlloyModel, AlloyMetaModel, AlloyTechnologyAdapter>, TechnologyAdapterResource<AlloyMetaModel, AlloyTechnologyAdapter> {

    /**
     * Return the effective metamodel addressed by this resource, its {@link ResourceData}
     *
     * @return
     */
    AlloyMetaModel getMetaModel();

    /**
     * Return the effective metamodel addressed by this resource, its {@link ResourceData}
     *
     * @return
     */
    @Override
    AlloyMetaModel getMetaModelData();

    @Getter(AlloyMetaModelResourceFactory.EXTENSION_KEY)
    String getModelFileExtension();

    @Setter(AlloyMetaModelResourceFactory.EXTENSION_KEY)
    void setModelFileExtension(String modelFileExtension);

    @Setter(value = AlloyMetaModelResourceFactory.PACKAGE_CLASSNAME_KEY)
    void setPackageClassName(String ePackage);

    @Setter(AlloyMetaModelResourceFactory.RESOURCE_FACTORY_KEY)
    void setResourceFactoryClassName(String resourceFactory);

    AlloyModelResource createAlloyModelResource(FlexoIODelegate<?> ioDelegate);

    abstract class AlloyMetaModelResourceImpl extends FlexoResourceImpl<AlloyMetaModel> implements AlloyMetaModelResource {

        protected static final Logger logger = Logger.getLogger(AlloyMetaModelResourceImpl.class.getPackage().getName());

        @Override
        public final AlloyMetaModel getMetaModelData() {

            try {
                return getResourceData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ResourceLoadingCancelledException e) {
                e.printStackTrace();
            } catch (FlexoException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public final AlloyMetaModel getMetaModel() {
            return getMetaModelData();
        }

        /**
         * Follow the link.
         *
         * @see org.openflexo.foundation.resource.FlexoResource#save()
         */
        @Override
        public void save() {
            logger.info("MetaModel is not supposed to be modified.");
        }

        @Override
        public Class<AlloyMetaModel> getResourceDataClass() {
            return AlloyMetaModel.class;
        }

        /**
         * Generic method used to retrieve in this resource an object with supplied objectIdentifier, userIdentifier, and type identifier<br>
         * <p>
         * Note that for certain resources, some parameters might not be used (for example userIdentifier or typeIdentifier)
         *
         * @param objectIdentifier
         * @param userIdentifier
         * @param typeIdentifier
         * @return
         */
        @Override
        public IFlexoOntologyConcept<AlloyTechnologyAdapter> findObject(String objectIdentifier, String userIdentifier, String typeIdentifier) {
            System.out.println("Dans EMFMetaModelResource, on me demande de trouver l'objet objectIdentifier=" + objectIdentifier + " userIdentifier=" + userIdentifier + " typeIdentifier=" + typeIdentifier);

            AlloyMetaModel metaModel = getMetaModel();

            IFlexoOntologyConcept<AlloyTechnologyAdapter> returned = metaModel.getClass(objectIdentifier);
            if (returned != null) {
                return returned;
            }
            returned = metaModel.getOntologyObject(objectIdentifier);
            return returned;
        }

        /**
         * Generic method used to retrieve in this resource an object with supplied objectIdentifier, userIdentifier, and type identifier<br>
         *
         * @param objectIdentifier
         * @param userIdentifier
         * @return
         */
        @Override
        public IFlexoOntologyConcept<AlloyTechnologyAdapter> findObject(String objectIdentifier, String userIdentifier) {
            return findObject(objectIdentifier, userIdentifier, null);
        }

        /**
         * Used to compute identifier of an object asserting this object is the {@link ResourceData} itself, or a {@link InnerResourceData}
         * object stored inside this resource
         *
         * @param object
         * @return a String identifying supplied object (semantics is composite key using userIdentifier and typeIdentifier)
         */
        @Override
        public String getObjectIdentifier(Object object) {

            if (object instanceof IFlexoOntologyConcept) {
                return ((IFlexoOntologyConcept<?>) object).getURI();
            }
            logger.warning("Unexpected object " + object);
            return null;
        }

        /**
         * Used to compute user identifier of an object asserting this object is the {@link ResourceData} itself, or a {@link InnerResourceData}
         * object stored inside this resource
         *
         * @param object
         * @return a String identifying author (user) of supplied object
         */
        @Override
        public String getUserIdentifier(Object object) {
            logger.warning("Unexpected object asking for userIdentifier" + object);
            return "unknownUserIdentifier";
        }

        @Override
        public AlloyTechnologyContextManager getTechnologyContextManager() {
            return (AlloyTechnologyContextManager) performSuperGetter(TECHNOLOGY_CONTEXT_MANAGER);
        }
    }
}