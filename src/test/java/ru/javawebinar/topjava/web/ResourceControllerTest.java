package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest{

    @Test
    void cssTest() throws Exception{
        perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
