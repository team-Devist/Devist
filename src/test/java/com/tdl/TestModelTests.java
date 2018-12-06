package com.tdl;


import com.tdl.model.TestModel;
import com.tdl.repository.TestModelRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
public class TestModelTests {
    @Autowired
    private TestModelRepository testModelRepository;

    @Test
    public void testTestModel() {
        TestModel testModel = new TestModel();
        testModel.setName("test");
        testModel.setContent("test content");
        testModelRepository.save(testModel);

        List<TestModel> testmodels = testModelRepository.findAll();
        Assert.assertEquals(testmodels.size(), 1);
        Assert.assertEquals(testmodels.get(0).getName(), "test");
        Assert.assertEquals(testmodels.get(0).getContent(), "test content");
    }
}
