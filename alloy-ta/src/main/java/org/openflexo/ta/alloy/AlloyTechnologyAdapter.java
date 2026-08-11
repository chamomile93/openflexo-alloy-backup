package org.openflexo.ta.alloy;

import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.openflexo.foundation.fml.FMLCompilationUnit;
import org.openflexo.foundation.fml.TechnologySpecificType;
import org.openflexo.foundation.fml.annotations.DeclareModelSlots;
import org.openflexo.foundation.fml.annotations.DeclareResourceFactories;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.ontology.technologyadapter.FlexoOntologyTechnologyContextManager;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.ITechnologySpecificFlexoResourceFactory;
import org.openflexo.foundation.technologyadapter.SpecificTypeInfo;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterBindingFactory;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.ta.alloy.fml.binding.AlloyBindingFactory;
import org.openflexo.ta.alloy.rm.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author chamomile93
 */
@DeclareModelSlots({ AlloyModelSlot.class })
@DeclareResourceFactories({ AlloyModelResourceFactory.class, AlloyMetaModelResourceFactory.class })
@FML(value = "Alloy Technology Adapter", description = "<html>This technology adapter provides model "
		+ "federation facilities to manage alloy source code" + "This technology adapter is build on AlloyAnalyzer "
		+ "technology" + "</html>")
public class AlloyTechnologyAdapter extends TechnologyAdapter<AlloyTechnologyAdapter> {
	static final Logger logger = Logger.getLogger(AlloyTechnologyAdapter.class.getPackage().getName());

	private static final String TAName = "Alloy Technology Adapter";
	private static final AlloyBindingFactory BINDING_FACTORY = new AlloyBindingFactory();
	private static final String LOCALIZATION_NAME = "FlexoLocalization" + "/AlloyTechnologyAdapter";
	private static final String IDENTIFIER = "Alloy";

	public AlloyTechnologyAdapter() {
		super();
	}

	@Override
	public String getName() {
		return TAName;
	}

	@Override
	public TechnologyAdapterBindingFactory getTechnologyAdapterBindingFactory() {
		return BINDING_FACTORY;
	}

	@Override
	public void ensureAllRepositoriesAreCreated(FlexoResourceCenter<?> rc) {
		super.ensureAllRepositoriesAreCreated(rc);
	}

	@Override
	public <I> boolean isIgnorable(FlexoResourceCenter<I> resourceCenter, I contents) {
		String contentFilePath = contents.toString();
		int contentFilePathLenght = contentFilePath.length();
		logger.log(Level.INFO, "rC= " + resourceCenter.getDisplayableName());
		logger.log(Level.INFO, "isIgnorable= " + contentFilePath);
		int idxSuffix =
				contentFilePathLenght - AlloyModelResourceFactory.ALS_FILE_EXTENSION.length();
		boolean isAlloyFile = contentFilePath.substring(idxSuffix, contentFilePathLenght)
				.equals(AlloyModelResourceFactory.ALS_FILE_EXTENSION);
		if (isAlloyFile) {
			logger.log(Level.INFO, "file is not ignorable= " + contentFilePath);
			return false;
		}
		logger.log(Level.INFO, "file is ignorable= " + contentFilePath);
		return true;
	}

	@Override
	protected <I> boolean isFolderIgnorable(FlexoResourceCenter<I> resourceCenter, I contents) {
		if (resourceCenter.isDirectory(contents)) {
			// TODO idk necessary?
//			for (I c : resourceCenter.getContents(contents)) {
//				if (resourceCenter.retrieveName(c).endsWith(JarBasedMetaModelResource.PROPERTIES_SUFFIX)) {
//					return true;
//				}
//			}
		}
		return false;
	}

	@Override
	public String serializeType(TechnologySpecificType<AlloyTechnologyAdapter> type, FMLCompilationUnit compilationUnit,
			boolean useTypeDefinitions) {
		// TODO idk
//		if (type instanceof EMFObjectIndividualType) {
//			EMFObjectIndividualType individualType = (EMFObjectIndividualType) type;
//			if (useTypeDefinitions && compilationUnit.getTypeDeclaration(type) != null) {
//				return compilationUnit.getTypeDeclaration(type).getAbbrev();
//			}
//			if (individualType.getOntologyClass() != null) {
//				EMFClassClass ontologyClass = individualType.getOntologyClass();
//				ElementImportDeclaration ontologyClassImport = compilationUnit.ensureElementImport(ontologyClass, false);
//				return "EMFObjectIndividualType(eClass=" + ontologyClassImport.getAbbrev() + ")";
//			}
//			return "EMFObjectIndividualType()";
//		}
		return super.serializeType(type, compilationUnit, useTypeDefinitions);
	}

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	protected String getLocalizationDirectory() {
		return LOCALIZATION_NAME;
	}

	/**
	 * Return the {@link FlexoOntologyTechnologyContextManager} for this technology
	 * shared by all {@link FlexoResourceCenter} declared in the scope of
	 * {@link FlexoResourceCenterService}
	 * 
	 * @return
	 */
	@Override
	public AlloyTechnologyContextManager getTechnologyContextManager() {
		return (AlloyTechnologyContextManager) super.getTechnologyContextManager();
	}

	@Override
	public AlloyTechnologyContextManager createTechnologyContextManager(
			FlexoResourceCenterService resourceCenterService) {
		return new AlloyTechnologyContextManager(this, resourceCenterService);

	}

	@Override
	protected void resourceCenterHasBeenInitialized(FlexoResourceCenter<?> rc) {
		super.resourceCenterHasBeenInitialized(rc);
	}

	@Override
	public void initTechnologySpecificTypes(TechnologyAdapterService taService) {
		// TODO
		// taService.registerTypeClass();
	}

	@Override
	public void activate() {
		logger.finest("START AlloyTechnologyAdapter.activate()");
		/**
		 * TODO this doesnt seem to activate correctly and throws exception at startup of maintainer with this TA
		 * one reason seems to be the MetaModelResourceFactory, see comments elsewhere
		 */
		logger.finest("AlloyTechnologyAdapter.super.activate()");
		super.activate();
		logger.finest("AlloyTechnologyAdapter.super.activate() done");
		logger.finest("AlloyTechnologyAdapter.activate() registerClassPathMetaModels");
		/**
		 * TODO forgot why we need to register "MetaModels"
		 * don't recall having defined a metamodel for Alloy grammar
		 */
		registerClasspathMetaModels();
		logger.finest("AlloyTechnologyAdapter.activate() registerClassPathMetaModels done");
		logger.finest("END AlloyTechnologyAdapter.activate()");
	}

	private AlloyMetaModelResource alloyMetaModelResource = null;

	public static String ALLOY_MM_NAME = "Alloy Metamodel";
	public static String ECORE_MM_URI = "http://www.eclipse.org/emf/2002/Ecore";
	private static String ECORE_MM_EXT = "ecore";
	private static String ECORE_MM_PKGCLSNAME = EcorePackageImpl.class.getName();
	private static String ECORE_MM_FACTORYCLSNAME = EcoreResourceFactoryImpl.class.getName();

	private void registerClasspathMetaModels() {
		logger.finest("START AlloyTechnologyAdapter.registerClassPathMetaModels()");

		/**
		 * TODO probably have to define one metamodel for Alloy in ecore or reuse one as I saw before in a research article
		 * as it stands I don't know what this do ? does it retrieve a representation in ecore ? or calculate one in ecore ?
		 * what is the representation ?
 		 */
		logger.finest("AlloyTechnologyAdapter.registerClassPathMetaModels() alloyMetaModelResource=" + alloyMetaModelResource);

		logger.finest("START AlloyTechnologyAdapter.registerClassPathMetaModels() retrieveResourceFromClassPath with  factoryClassName = " + ECORE_MM_FACTORYCLSNAME + " ALLOY_MM_NAME= " + ALLOY_MM_NAME + " ECORE_MM_URI= " + ECORE_MM_URI + " ECORE_MM_EXT= " + ECORE_MM_EXT + " ECORE_MM_PKGCLSNAME= " + ECORE_MM_PKGCLSNAME + " getTechnologyContextManager=" + getTechnologyContextManager().getClass().getSimpleName());
		alloyMetaModelResource = getAlloyMetaModelResourceFactory().retrieveResourceFromClassPath(ALLOY_MM_NAME, ECORE_MM_URI, ECORE_MM_EXT,
				ECORE_MM_PKGCLSNAME, ECORE_MM_FACTORYCLSNAME, getTechnologyContextManager());

		logger.finest("END AlloyTechnologyAdapter.registerClassPathMetaModels() retrieveResourceFromClassPath  with  factoryClassName = " + ECORE_MM_FACTORYCLSNAME + " ALLOY_MM_NAME= " + ALLOY_MM_NAME + " ECORE_MM_URI= " + ECORE_MM_URI + " ECORE_MM_EXT= " + ECORE_MM_EXT + " ECORE_MM_PKGCLSNAME= " + ECORE_MM_PKGCLSNAME + " getTechnologyContextManager=" + getTechnologyContextManager().getClass().getSimpleName());

		logger.finest("AlloyTechnologyAdapter.registerClassPathMetaModels() alloyMetaModelResource=" + alloyMetaModelResource);

		logger.finest("END AlloyTechnologyAdapter.registerClassPathMetaModels()");
	}

	@Override
	public List<ITechnologySpecificFlexoResourceFactory<?, ?, ?>> getResourceFactories() {
		return super.getResourceFactories();
	}

	private AlloyMetaModelResourceFactory getAlloyMetaModelResourceFactory() {
		return getResourceFactory(AlloyMetaModelResourceFactory.class);
	}


	@Override
	public <T extends TechnologySpecificType<AlloyTechnologyAdapter>> T instantiateType(
			SpecificTypeInfo<AlloyTechnologyAdapter> specificTypeInfo) {
		T returned = null;
		// TODO idf if make sense ?
//		if (specificTypeInfo.getTechnologySpecificTypeClass().equals(AlloyObject.class)) {
//			if (specificTypeInfo.getParameter("org.alloytools.xyz.AlloyClass") != null) {
//				// TODO probably smth like "AlloySignature" here
////				EMFClassClass type = (EMFClassClass) specificTypeInfo.getParameter("eClass");
////				returned = (T) EMFObjectIndividualType.getEMFObjectIndividualOfClass(type);
//			} else {
////				returned = (T) EMFObjectIndividualType.UNDEFINED_EMF_INDIVIDUAL_TYPE;
//			}
//		}
		if (returned != null) {
			returned.registerSpecificTypeInfo(specificTypeInfo);
			return returned;
		}
		return null;
	}

	public AlloyModelResourceFactory getAlloyResourceFactory() {
		return getResourceFactory(AlloyModelResourceFactory.class);
	}

	public <I> AlloyModelResourceRepository<I> getAlloyResourceRepository(
			FlexoResourceCenter<I> resourceCenter) {
		AlloyModelResourceRepository<I> returned = resourceCenter.retrieveRepository(AlloyModelResourceRepository.class, this);
		if (returned == null) {
			returned = AlloyModelResourceRepository.instanciateNewRepository(this, resourceCenter);
			resourceCenter.registerRepository(returned, AlloyModelResourceRepository.class, this);
		}
		return returned;
	}

	public <I> AlloyModelRepository<I> getAlloyModelRepository(FlexoResourceCenter<I> resourceCenter) {
		AlloyModelRepository<I> returned =
				resourceCenter.retrieveRepository(AlloyModelRepository.class,
						this);
		if (returned == null) {
			returned = AlloyModelRepository.instanciateNewRepository(this,
					resourceCenter);
			resourceCenter.registerRepository(returned,
					AlloyModelRepository.class, this);
		}
		return returned;
	}

	public void newMetaModelWasRegistered(AlloyMetaModelResource mmResource,
                                          FlexoResourceCenter<?> resourceCenter) {

		logger.info("Lookup models conform to " + mmResource);
		for (FlexoResourceCenter<?> rc : getServiceManager().getResourceCenterService().getResourceCenters()) {
			// Then we iterate on all resources found in the resource factory
			handleNewMetaModelRegistered(mmResource, rc);
		}

		getTechnologyContextManager().newMetaModelWasRegistered(mmResource, resourceCenter);
	}

	private <I> void handleNewMetaModelRegistered(AlloyMetaModelResource mmResource, FlexoResourceCenter<I> rc) {
		for (I serializationArtefact : rc) {
			if (!isSerializationArtefactIgnorable(rc, serializationArtefact)) {
				AlloyModelResourceFactory resourceFactory =
						getAlloyModelResourceFactory();
				if (resourceFactory.getRegisteredResource(serializationArtefact) == null) {
					try {
						AlloyModelResource modelResource =
								resourceFactory.retrieveResource(serializationArtefact, rc);
						logger.info("Registered new model " + modelResource + " conform to " + mmResource);
					} catch (ModelDefinitionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public AlloyModelResourceFactory getAlloyModelResourceFactory() {
		return getResourceFactory(AlloyModelResourceFactory.class);
	}

	public <I> AlloyMetaModelRepository<I> getAlloyMetaModelRepository(FlexoResourceCenter<I> resourceCenter) {
		AlloyMetaModelRepository<I> returned =
				resourceCenter.retrieveRepository(AlloyMetaModelRepository.class,	this);
		if (returned == null) {
			returned = AlloyMetaModelRepository.instanciateNewRepository(this,
					resourceCenter);
			resourceCenter.registerRepository(returned,
					AlloyMetaModelRepository.class, this);
		}
		return returned;
	}
}
