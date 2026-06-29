package org.openflexo.ta.alloy.model;

/**
 *
 * Copyright (c) 2018, Openflexo
 * <p>
 * This file is part of OpenflexoTechnologyAdapter, a component of the software infrastructure
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.ontology.IFlexoOntologyIndividual;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.ta.alloy.AbstractAlloyTest;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.ta.alloy.rm.AlloyModelResource;
import org.openflexo.ta.alloy.rm.AlloyModelResourceRepository;
import org.openflexo.test.OrderedRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * @author chamomile93
 */
@RunWith(OrderedRunner.class)
//TODO Review existing tests
public class TestLoadAlloyModel extends AbstractAlloyTest {
    public static final String HELLO_WORLD_ALLOY_URI = "http://www.openflexo.org/test/alloy/TestResourceCenter/AlloyCode/HelloWorld.als";
    public static final String HELLO_WORLD_ALLOY_FILE_NAME = "HelloWorld.als";
    protected static final Logger logger = Logger.getLogger(TestLoadAlloyModel.class.getPackage().getName());
    private static final int EXPECTED_ALL_RESOURCES_NUMBER_EXACTLY = 6;
    private static final int EXPECTED_ALLOY_RESOURCES_NUMBER_EXACTLY = 3;
    private static final int EXPECTED_RESOURCE_CENTER_NUMBER_MINIMUM = 1;
    private static final int EXPECTED_ONE_INDIVIDUAL_NUMBER = 1;
    private AlloyTechnologyAdapter alloyTechnologyAdapter = null;
    private FlexoResourceCenterService rcService = null;
    private FlexoResourceCenter<?> alloyResourceCenter = null;
    private AlloyModelResourceRepository<?> alloyModelResourceRepository = null;

    @Before
    public void setupInitializeServiceManagerWithAlloyTechnologyAdapter() throws Exception {
        log("setupInitializeServiceManager(AlloyTechnologyAdapter)");
        instanciateTestServiceManager(AlloyTechnologyAdapter.class);
        for (FlexoResource<?> r : serviceManager.getResourceManager().getRegisteredResources()) {
            log("FlexoResource registered > " + r.getURI());
        }
    }

    @After
    public void tearDownInitializeServiceManager() {
        serviceManager.stopAllServices();
        alloyTechnologyAdapter = null;
        rcService = null;
        alloyModelResourceRepository = null;
        alloyResourceCenter = null;
        // TODO smth else ?
    }

    @Test
    public void testAlloyTechnologyAdapterIsLoadedSucceed() throws Exception {
        //TODO might need review, may not be relevant for testing the
        // TechnologyAdapter, not sure this is tested elsewhere.
        alloyTechnologyAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(AlloyTechnologyAdapter.class);
        assertNotNull(alloyTechnologyAdapter);
    }

    @Test
    public void testResourcesNotEmptyInResourceCenterSucceed() {
        //TODO might need review, the title is not precise enough
        log("testAlloyResourceLoading()");
        for (FlexoResourceCenter<?> resourceCenter : serviceManager.getResourceCenterService().getResourceCenters()) {
            Collection<FlexoResource<?>> resources = resourceCenter.getAllResources();
            assertNotNull(resources);
            assertNotEquals(Collections.EMPTY_LIST, resources);
            assertFalse(resources.isEmpty());
            assertTrue(resources.size() > 0);
            assertNotNull(resources);
            assertFalse(resources.isEmpty());
            assertTrue(resources.size() > 0);
            assertEquals(EXPECTED_ALL_RESOURCES_NUMBER_EXACTLY, resources.size());
            for (FlexoResource<?> flexoResource : resources) {
                log("resource class simpleName= " + flexoResource.getClass().getSimpleName());
                log("resource class simpleName= " + flexoResource.getResourceDataClass().getSimpleName());
                log("flexoResource= " + flexoResource);
                assertNotNull(flexoResource);
                //TODO check the traces for "als" resources for ".null.null"
            }
        }
    }

    // TODO test alloy-ta ? AlloyMS ? smth else ?

    @Test
    public void testAllResourceRepositoryIsNotNullSucceed() {
        //TODO might need review, since we get an iterator on resourceCenter,
        // its not null
        alloyTechnologyAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(AlloyTechnologyAdapter.class);
        for (FlexoResourceCenter<?> resourceCenter : serviceManager.getResourceCenterService().getResourceCenters()) {
            // should return a subtype of ResourceRepository
            log("rcName= " + resourceCenter.getDisplayableName());
            log("baseURI= " + resourceCenter.getDefaultBaseURI());

            // TODO idf why must do this
            alloyModelResourceRepository = alloyTechnologyAdapter.getAlloyResourceRepository(resourceCenter);
            assertNotNull(alloyModelResourceRepository);
        }
    }

    @Test
    public void testExistExactlyOneResourceCenterForAlloyTechnologyAdapterSuceed() {
        //TODO might need review, what's the reason for exactly one
        // resourceCenter ?
        rcService = serviceManager.getResourceCenterService();
        Collection<FlexoResourceCenter<?>> resourceCenters = rcService.getResourceCenters();

        assertNotNull(resourceCenters);
        assertFalse(resourceCenters.isEmpty());
        assertTrue(resourceCenters.size() > 0);
        assertEquals(EXPECTED_RESOURCE_CENTER_NUMBER_MINIMUM, resourceCenters.size());
    }

    @Test
    public void testExistsAlloyResourceInResourceRepositorySucceed() {
        rcService = serviceManager.getResourceCenterService();
        alloyTechnologyAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(AlloyTechnologyAdapter.class);
        alloyResourceCenter = rcService.getResourceCenters().get(0);

        alloyModelResourceRepository = alloyTechnologyAdapter.getAlloyResourceRepository(alloyResourceCenter);
        Collection<AlloyModelResource> resources = alloyModelResourceRepository.getAllResources();
        assertNotNull(resources);

        assertFalse(resources.isEmpty());
        assertTrue(resources.size() > 0);
        assertEquals(EXPECTED_ALLOY_RESOURCES_NUMBER_EXACTLY, resources.size());
        assertNotEquals(EXPECTED_ALL_RESOURCES_NUMBER_EXACTLY, resources.size());
    }

    @Test
    public void testGetAlloyResourceFromSerializationNameSucceed() throws IOException {
        //GIVEN
        rcService = serviceManager.getResourceCenterService();
        alloyResourceCenter = rcService.getResourceCenters().get(0);
        //alloyResourceCenter = makeNewDirectoryResourceCenterFromExistingResourceCenter(serviceManager, alloyResourceCenter);
        alloyTechnologyAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(AlloyTechnologyAdapter.class);
        alloyModelResourceRepository = alloyTechnologyAdapter.getAlloyResourceRepository(alloyResourceCenter);
        Collection<AlloyModelResource> resources = alloyModelResourceRepository.getAllResources();

        //WHEN
        AlloyModelResource helloWorldAlloyModelResourceFromSerializationName = getResourceWithSerializationArtefactWithName(resources, HELLO_WORLD_ALLOY_FILE_NAME);
        //THEN
        assertNotNull(helloWorldAlloyModelResourceFromSerializationName);
    }

    @Test
    public void testLoadOneAlloyResourceSucceed() {
        AlloyModelResource helloWorldAlloyModelResourceFromServiceManager = (AlloyModelResource) serviceManager.getResourceManager().getResource(HELLO_WORLD_ALLOY_URI);
        assertNotNull(helloWorldAlloyModelResourceFromServiceManager);

        try {
            helloWorldAlloyModelResourceFromServiceManager.loadResourceData();
        } catch (ResourceLoadingCancelledException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FlexoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLoadAllResourceSucceed() {
        //GIVEN
        rcService = serviceManager.getResourceCenterService();
        alloyResourceCenter = rcService.getResourceCenters().get(0);
        alloyTechnologyAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(AlloyTechnologyAdapter.class);
        alloyModelResourceRepository = alloyTechnologyAdapter.getAlloyResourceRepository(alloyResourceCenter);
        Collection<AlloyModelResource> resources = alloyModelResourceRepository.getAllResources();

        for (AlloyModelResource alloyModelResource : resources) {
            try {
                alloyModelResource.loadResourceData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ResourceLoadingCancelledException e) {
                e.printStackTrace();
            } catch (FlexoException e) {
                e.printStackTrace();
            }
            assertNotNull(alloyModelResource.getLoadedResourceData());
            log("URI of document: " + alloyModelResource.getURI());
            log("ResourceData: " + alloyModelResource.getLoadedResourceData());
        }
    }

    @Test
    public void testReadHelloWorldInAlloyShouldSucced() {
        AlloyModelResource helloWorldRes = (AlloyModelResource) serviceManager.getResourceManager().getResource(HELLO_WORLD_ALLOY_URI);

        AlloyModel basicModel;
        List<? extends IFlexoOntologyIndividual<AlloyTechnologyAdapter>> definitionsList;
        AlloyObjectIndividual definition;

        try {
            basicModel = helloWorldRes.loadResourceData();
            assertNotNull(basicModel);

            /*
             * the following might explain why logged resource uri contains ".null.null"
             * helloWorldRes.toString(): 
AlloyModelResource[URI=null
alloyModelResource=null
container=null
flexoIODelegate=InJarIODelegate
name=HelloWorld.als
resourceCenter=JarResourceCenter
revision=null
technologyAdapter=org.openflexo.ta.alloy.AlloyTechnologyAdapter@52d97ab6
technologyContextManager=org.openflexo.ta.alloy.AlloyTechnologyContextManager@552ffa44
version=null
] 
             */
            
            //TODO not sure this makes sense, probably need to load;get
            definitionsList = basicModel.getIndividuals();
            assertNotNull(definitionsList);
            //TODO FAIL
            assertEquals(EXPECTED_ONE_INDIVIDUAL_NUMBER, definitionsList.size());
            definition = (AlloyObjectIndividual) definitionsList.get(0);
            log("individual definition = " + definition);
            assertNotNull(definition);

            //TODO this assumes "load", which one ?
            basicModel = helloWorldRes.getLoadedResourceData();
            assertNotNull(basicModel);
            definitionsList = basicModel.getIndividuals();
            assertNotNull(definitionsList);
            //TODO FAIL
            assertEquals(EXPECTED_ONE_INDIVIDUAL_NUMBER, definitionsList.size());
            definition = (AlloyObjectIndividual) definitionsList.get(0);
            log("individual definition = " + definition);
            assertNotNull(definition);

            basicModel = helloWorldRes.getResourceData();
            assertNotNull(basicModel);
            definitionsList = basicModel.getIndividuals();
            assertNotNull(definitionsList);
            //TODO FAIL
            assertEquals(EXPECTED_ONE_INDIVIDUAL_NUMBER, definitionsList.size());
            definition = (AlloyObjectIndividual) definitionsList.get(0);
            log("individual definition = " + definition);
            assertNotNull(definition);

            basicModel = helloWorldRes.getModelData();
            assertNotNull(basicModel);
            definitionsList = basicModel.getIndividuals();
            assertNotNull(definitionsList);
            //TODO FAIL
            assertEquals(EXPECTED_ONE_INDIVIDUAL_NUMBER, definitionsList.size());
            definition = (AlloyObjectIndividual) definitionsList.get(0);
            log("individual definition = " + definition);
            assertNotNull(definition);

            basicModel = helloWorldRes.getModel();
            assertNotNull(basicModel);
            definitionsList = basicModel.getIndividuals();
            assertNotNull(definitionsList);
            //TODO FAIL
            assertEquals(EXPECTED_ONE_INDIVIDUAL_NUMBER, definitionsList.size());
            definition = (AlloyObjectIndividual) definitionsList.get(0);
            log("individual definition = " + definition);
            assertNotNull(definition);

        } catch (ResourceLoadingCancelledException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FlexoException e) {
            throw new RuntimeException(e);
        }

        //TODO more test relating "reading"
    }

    @Test
    public void testWriteHelloWorldInAlloyShouldSucceed() {
        fail();

        // TODO change to org.alloytools.parser/reader, whatever
        // didn't pay attention before but this is "jackson" dependencies
        //ObjectMapper mapper = new ObjectMapper();

        //TODO here is some inspiration from json-ta
        try {
            // TODO might change this to a unit test, instead of this "main()"
            // TODO try this as with TestEra code
            // JsonNode root = mapper.readTree(f);

//				String nom = root.get("nom").asText();
//				int age = root.get("age").asInt();
//
//				JsonNode adresse = root.get("adresse");
//				String ville = adresse.get("ville").asText();
//
//				JsonNode hobbies = root.get("hobbies");
//				for (JsonNode hobby : hobbies) {
//					System.out.println("Hobby: " + hobby.asText());
//				}
//
//				System.out.println("Nom: " + nom);
//				System.out.println("Ville: " + ville);
//
//				String jsonPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
//				System.out.println("PP: " + jsonPretty);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listRegisteredResources() {
        log("listRegisteredResources()");

        serviceManager.getResourceCenterService().getResourceCenters().forEach(rc -> {
            log("Resource center : " + rc.getDefaultBaseURI());
        });

        log(">>> Ressources enregistrées :");
        serviceManager.getResourceManager().getRegisteredResources().forEach(r -> log(" rUri= " + r.getURI() + " : " + r));
    }
}
