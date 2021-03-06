/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.models.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.modules.activiti.model.entities.Model;
import org.mule.munit.runner.functional.FunctionalMunitSuite;

/**
 * 
 * @author bfattouh
 * 
 */
public class DeleteModelTestCases extends FunctionalMunitSuite {

	private Map<String, Object> testData = new HashMap<String, Object>();
	private MuleEvent requestEvent;
	private MuleEvent resultEvent;
	private Model model;

	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}

	@Before
	public void setup() throws Exception {
		testData.put("aname", "model-test");
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("create-model", requestEvent);
		model = (Model) resultEvent.getMessage().getPayload();
		assertNotNull(model);
	}

	@After
	public void tearDown() throws Exception {
		testData.put("modelId", model.getId());
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("delete-model-by-id", requestEvent);
	}

	@Test
	public void testDeleteModel() throws Exception {
		testData.put("modelId", model.getId());
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("delete-model-by-id", requestEvent);
		String statusCode = (String) resultEvent.getMessage().getPayload();
		assertNotNull(statusCode);
		assertEquals("204", statusCode);
	}

	@Test
	public void testDeleteInexistingModel() throws Exception {
		testData.put("modelId", "testId");
		requestEvent = testEvent(muleMessageWithPayload(testData));
		resultEvent = runFlow("delete-model-by-id", requestEvent);
		String statusCode = (String) resultEvent.getMessage().getPayload();
		assertNotNull(statusCode);
		assertEquals("404", statusCode);
	}

}
