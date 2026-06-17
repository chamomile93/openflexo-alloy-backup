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
public interface AlloyModelResource extends FlexoModelResource<AlloyModel,
        AlloyMetaModel,
		AlloyTechnologyAdapter, AlloyTechnologyAdapter>,
		TechnologyAdapterResource<AlloyModel, AlloyTechnologyAdapter> {

	public static final String TECHNOLOGY_CONTEXT_MANAGER = "technologyContextManager";
	public static final String ALS_EXTENSION = AlloyModelResourceFactory.ALS_FILE_EXTENSION;

	AlloyModelResource getAlloyResource();

	@Override
	@Getter(value = TECHNOLOGY_CONTEXT_MANAGER, ignoreType = true)
	public AlloyTechnologyContextManager getTechnologyContextManager();

	@Setter(TECHNOLOGY_CONTEXT_MANAGER)
	public void setTechnologyContextManager(AlloyTechnologyContextManager technologyContextManager);

	abstract class AlloyModelResourceImpl extends FlexoResourceImpl<AlloyModel> implements AlloyModelResource {

		private static final Logger logger =
				Logger.getLogger(AlloyModelResource.class.getPackage().getName());

		/** Model Resource. */
		protected AlloyModelResource modelResource;

		/**
		 * Load the &quot;real&quot; load resource data of this resource.
		 *
		 * @return the resource data.
		 * @throws ResourceLoadingCancelledException
		 * @throws FileNotFoundException
		 * @throws FlexoException
		 */
		@Override
		public AlloyModel loadResourceData() throws ResourceLoadingCancelledException, FileNotFoundException, FlexoException {
			AlloyModelConverter converter = new AlloyModelConverter();
			AlloyModel resourceData;
			//TODO do we need a metamodel ?
//			FlexoMetaModelResource<AlloyModel, AlloyMetaModel, AlloyTechnologyAdapter> metaModelResource = getMetaModelResource();
//			AlloyMetaModel metaModelData = metaModelResource.getMetaModelData();
			AlloyModelResource alloyModelResource = getAlloyResource();
			resourceData =
					converter.convertModel(alloyModelResource);
			setResourceData(resourceData);
			resourceData.setResource(this);
			return resourceData;
		}

		@Override
		public FlexoMetaModelResource<AlloyModel, AlloyMetaModel,
				AlloyTechnologyAdapter> getMetaModelResource() {
			logger.warning("FlexoMetaModelResource() not fully implemented in" +
					" AlloyModelResource");
			// TODO: implement this and extends cardinality

			return getTechnologyContextManager().getMetaModel(new File(modelResource.getURI()));
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
            getAlloyResource().save();
            logger.info("Wrote " + getIODelegate().toString());
        }

		/**
		 * Getter of Alloy Model Resource.
		 *
		 * @return the modelResource value
		 */
		@Override
		public AlloyModelResource getAlloyResource() {
//			if (modelResource == null) {
//				AlloyMetaModelResource mmResource =
//						(AlloyMetaModelResource) getMetaModelResource();
//				if (mmResource == null) {
//					logger.warning("AlloyModel has no meta-model !!!");
//					return null;
//				}
//				else {
//					if (!mmResource.isLoaded()) {
//						try {
//							mmResource.loadResourceData();
//						} catch (FileNotFoundException e) {
//							logger.warning("Cannot load Alloy MetaModel");
//							return null;
//						} catch (ResourceLoadingCancelledException e) {
//							logger.warning("Cannot load Alloy MetaModel");
//							return null;
//						} catch (FlexoException e) {
//							logger.warning("Cannot load Alloy MetaModel");
//							return null;
//						}
//					}
//
//				}
//				modelResource =
//						mmResource.createAlloyModelResource(getIODelegate());
			return modelResource;
		}

		@Override
		public Class<AlloyModel> getResourceDataClass() {
			return AlloyModel.class;
		}
//
//		/**
//		 * Generic method used to retrieve in this resource an object with supplied objectIdentifier, userIdentifier, and type identifier<br>
//		 *
//		 * Note that for certain resources, some parameters might not be used (for example userIdentifier or typeIdentifier)
//		 *
//		 * @param objectIdentifier
//		 * @param userIdentifier
//		 * @param typeIdentifier
//		 * @return
//		 */
//		@Override
//		public FlexoObject findObject(String objectIdentifier, String userIdentifier, String typeIdentifier) {
//			System.out.println("Dans EMFModelResource, on me demande de trouver l'objet objectIdentifier=" + objectIdentifier
//					+ " userIdentifier=" + userIdentifier + " typeIdentifier=" + typeIdentifier);
//			// return getFlexoObject(Long.parseLong(objectIdentifier), userIdentifier);
//			return null;
//		}
//
//		/**
//		 * Used to compute identifier of an object asserting this object is the {@link ResourceData} itself, or a {@link InnerResourceData}
//		 * object stored inside this resource
//		 *
//		 * @param object
//		 * @return a String identifying supplied object (semantics is composite key using userIdentifier and typeIdentifier)
//		 */
//		@Override
//		public String getObjectIdentifier(Object object) {
//
//			if (object instanceof AEMFMetaModelObjectImpl) {
//				EObject eObject = ((AEMFMetaModelObjectImpl) object).getObject();
//				return EcoreUtil.getID(eObject);
//			/*if (eObject instanceof ENamedElement) {
//				return ((ENamedElement) eObject).getName();
//			}
//			else {
//				logger.warning("Could not find id for " + object);
//				return null;
//			}*/
//			}
//			logger.warning("Unexpected object " + object);
//			return null;
//		}
//
//		/**
//		 * Used to compute user identifier of an object asserting this object is the {@link ResourceData} itself, or a {@link InnerResourceData}
//		 * object stored inside this resource
//		 *
//		 * @param object
//		 * @return a String identifying author (user) of supplied object
//		 */
//		@Override
//		public String getUserIdentifier(Object object) {
//			return "FLX";
//		}
//
//		private XMIMetaData metaData;
//
//		@Override
//		public <I> XMIMetaData getMetaData(FlexoResourceCenter<I> resourceCenter) {
//			if (metaData == null) {
//				metaData = findMetaData(resourceCenter, true);
//			}
//			return metaData;
//		}
//
//		private <I> XMIMetaData findMetaData(FlexoResourceCenter<I> resourceCenter, boolean forceRebuild) {
//			if (resourceCenter instanceof FlexoProject) {
//				resourceCenter = ((FlexoProject<I>) resourceCenter).getDelegateResourceCenter();
//			}
//
//			if (resourceCenter instanceof FileSystemBasedResourceCenter) {
//				FileSystemMetaDataManager metaDataManager = ((FileSystemBasedResourceCenter) resourceCenter).getMetaDataManager();
//				File file = (File) getIODelegate().getSerializationArtefact();
//
//				if (!forceRebuild && (file.lastModified() < metaDataManager.metaDataLastModified(file))) {
//					// OK, in this case the metadata file is there and more recent than xml file
//					// Attempt to retrieve metadata from cache
//					return new XMIMetaData(metaDataManager, file);
//				}
//				else {
//					// No way, metadata are either not present or older than file version, we should parse XML file, continuing...
//				}
//			}
//
//			System.out.println("Retrieve info from file for " + this);
//			XMIMetaData returned = new XMIMetaData(resourceCenter.getXMLRootElementInfo((I) getIODelegate().getSerializationArtefact()));
//
//			if (resourceCenter instanceof FileSystemBasedResourceCenter && returned != null) {
//				// Save metadata !!!
//				FileSystemMetaDataManager metaDataManager = ((FileSystemBasedResourceCenter) resourceCenter).getMetaDataManager();
//				File file = (File) getIODelegate().getSerializationArtefact();
//				returned.save(metaDataManager, file);
//			}
//
//			return returned;
//		}
//
//		private String metaModelResourceURI;
//
//		@Override
//		public void setMetaModelResourceURI(String mmURI) {
//			metaModelResourceURI = mmURI;
//		}
//
//		@Override
//		public String getMetaModelResourceURI() {
//			if (getMetaModelResource() != null) {
//				return getMetaModelResource().getURI();
//			}
//			return metaModelResourceURI;
//		}

		@Override
		public AlloyTechnologyContextManager getTechnologyContextManager() {
			return (AlloyTechnologyContextManager) performSuperGetter(TECHNOLOGY_CONTEXT_MANAGER);
		}

	}

}
