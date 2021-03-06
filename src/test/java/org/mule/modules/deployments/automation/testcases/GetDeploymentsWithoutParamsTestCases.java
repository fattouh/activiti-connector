/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.deployments.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.modules.activiti.deployment.entities.Deployment;
import org.mule.modules.activiti.deployment.entities.DeploymentWrapper;
import org.mule.munit.runner.functional.FunctionalMunitSuite;

/**
 * 
 * @author Bouchaib Fattouh - Appnovation Technologies
 * 
 */
public class GetDeploymentsWithoutParamsTestCases extends FunctionalMunitSuite {

	private Map<String, Object> testData = new HashMap<String, Object>();
	private Deployment deployment;
	private MuleEvent requestEvent;
	private MuleEvent resultEvent;

	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}

	@Before
	public void setup() throws Exception {
		testData.clear();
		testData.put("deploymentFilePath",
				"src/test/resources/create-account.bar");
		testData.put("tenantId", "myTenantId");
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("create-deployment", requestEvent);
		deployment = (Deployment) resultEvent.getMessage().getPayload();
	}

	@After
	public void tearDown() throws Exception {
		testData.clear();
		testData.put("deploymentId", deployment.getId());
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("delete-deployment-by-id", requestEvent);
		String statusCode = (String) resultEvent.getMessage().getPayload();
		System.out.println("Delete Result : " + statusCode);
	}

	@Test
	public void testGetDeploymentsWithoutParams() throws Exception {
		requestEvent = testEvent(muleMessageWithPayload(null));
		resultEvent = runFlow("get-deployments", requestEvent);
		DeploymentWrapper deploymentWapper = (DeploymentWrapper) resultEvent
				.getMessage().getPayload();
		assertNotNull(deploymentWapper);
		assertTrue(!deploymentWapper.getData().isEmpty());
		assertTrue(deploymentWapper.getData().size() > 0);
		Deployment expectedDep = deploymentWapper
				.getDeploymentByName(deployment.getName());
		assertEquals(expectedDep.getName(), deployment.getName());
		assertEquals(expectedDep.getDeploymentTime(),
				deployment.getDeploymentTime());
		assertEquals(expectedDep.getTenantId(), deployment.getTenantId());
		assertEquals(expectedDep.getUrl(), deployment.getUrl());
	}

	// @Test
	public void testGetDeploymentsWithCategoryParam() throws Exception {
		testData.clear();
		testData.put("category", deployment.getCategory());
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("get-deployments", requestEvent);
		DeploymentWrapper deploymentWapper = (DeploymentWrapper) resultEvent
				.getMessage().getPayload();
		assertNotNull(deploymentWapper);
		assertTrue(!deploymentWapper.getData().isEmpty());
		assertTrue(deploymentWapper.getData().size() > 0);
		Deployment expectedDep = deploymentWapper
				.getDeploymentByName(deployment.getName());
		assertEquals(expectedDep.getName(), deployment.getName());
		assertEquals(expectedDep.getDeploymentTime(),
				deployment.getDeploymentTime());
		assertEquals(expectedDep.getTenantId(), deployment.getTenantId());
		assertEquals(expectedDep.getUrl(), deployment.getUrl());
	}

	// @Test
	public void testGetDeploymentsWithCategoryNotEqualsParam() throws Exception {
		testData.clear();
		testData.put("categoryNotEquals", "dummy-category");
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("get-deployments", requestEvent);
		DeploymentWrapper deploymentWapper = (DeploymentWrapper) resultEvent
				.getMessage().getPayload();
		assertNotNull(deploymentWapper);
		assertTrue(!deploymentWapper.getData().isEmpty());
		assertTrue(deploymentWapper.getData().size() > 0);
		Deployment expectedDep = deploymentWapper
				.getDeploymentByName(deployment.getName());
		assertEquals(expectedDep.getName(), deployment.getName());
		assertEquals(expectedDep.getDeploymentTime(),
				deployment.getDeploymentTime());
		assertEquals(expectedDep.getTenantId(), deployment.getTenantId());
		assertEquals(expectedDep.getUrl(), deployment.getUrl());
	}

}
