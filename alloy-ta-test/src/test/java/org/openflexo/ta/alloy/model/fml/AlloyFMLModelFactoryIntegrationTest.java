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

package org.openflexo.ta.alloy.model.fml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.fml.FMLTechnologyAdapter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.foundation.test.fml.AbstractModelFactoryIntegrationTestCase;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * Test instanciation of an FMLModelFactory<br>
 * Here the model factory is instantiated with Alloy technology adapters
 *
 */
@RunWith(OrderedRunner.class)
public class AlloyFMLModelFactoryIntegrationTest extends AbstractModelFactoryIntegrationTestCase {

	private static final Logger logger = FlexoLogger
			.getLogger(AlloyFMLModelFactoryIntegrationTest.class.getPackage().getName());

	/**
	 * Instanciate test ServiceManager
	 */
	@Before
	public void setupInitializeServiceManager() {
		log("setupInitializeServiceManager()");
		instanciateTestServiceManager();
	}
	
	@After
	public void tearDownInitializeServiceManager() {
		log("tearDownInitializeServiceManager()");
		serviceManager.stopAllServices();
		// TODO smth else ?
	}
	
	@Test
	@TestOrder(1)
	public void checkServiceManagerIsInitializedWithAlloyTechnologyAdapter() {
		log("checkServiceManagerIsInitializedWithAlloyTechnologyAdapter() {()");
		assertNotNull(serviceManager.getService(FlexoResourceCenterService.class));
		assertNotNull(serviceManager.getService(TechnologyAdapterService.class));

		TechnologyAdapterService taService = serviceManager.getTechnologyAdapterService();
		assertEquals(taService, serviceManager.getService(TechnologyAdapterService.class));
		
		// it seems that one intent is to demonstrate that given
		// a TechnologyAdapterService during initialization, 
		// then it discovers all Technology Adapters in one (executing)? environment  
		assertNotNull(taService.getTechnologyAdapter(AlloyTechnologyAdapter.class));
	}

	/**
	 * Check the presence of {@link FMLTechnologyAdapter}, instanciate
	 * FMLModelFactory with this TA
	 */
	@Test
	@TestOrder(2)
	public void checkAlloyFMLTechnologyAdapter() {
		log("BEGIN checkAlloyFMLTechnologyAdapter()");

		testVirtualModelModelFactoryWithTechnologyAdapter(
				serviceManager.getTechnologyAdapterService().getTechnologyAdapter(AlloyTechnologyAdapter.class));

		log("END checkAlloyFMLTechnologyAdapter()");
	}

}
