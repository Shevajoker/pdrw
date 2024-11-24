package com.pdrw.pdrw.pinskdrevru;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.service.impl.PinskdrevRuServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Log4j2
@RunWith(SpringRunner.class)
public class PinskdrevRuFeatureSteps {

    private PinskdrevRuServiceImpl pinskdrevRuService;
    private List<PinskdrevRu> pinskdrevRuList;
    private int pinskdrevRuListSize = 0;


    @Given("^I have a method which find a list of Pinskdrev-ru items")
    public void i_have_a_method_which_find_a_list_of_pinskdrev_ru_items() {

        findPinskdrevRuList();
        Assert.assertFalse(pinskdrevRuList.isEmpty());
    }

    @When("I connect to pinskdrev-ru table")
    public void i_connect_to_pinskdrev_ru_table() {
        pinskdrevRuListSize = pinskdrevRuList.size();
    }

    @Then("The list size is more then one")
    public void the_list_size_is_more_then_one() {
        Assert.assertTrue(pinskdrevRuListSize > 0);
    }

    private void findPinskdrevRuList() {
        pinskdrevRuList.add(new PinskdrevRu());
    }
}
