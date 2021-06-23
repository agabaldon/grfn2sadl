package com.ge.research.sadl.darpa.aske.rest.grasen.service;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Checks that the application's REST endpoint can receive a JSON file
 * and will return a SADL file generated from the JSON file.
 */
@WebMvcTest(GrasenController.class)
@AutoConfigureMockMvc
public class GrasenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void translateShouldReturnFile() throws Exception {
        String jsonContent =
                "{\n"
                + "  \"uid\": \"ebe21368-98c7-5205-1e01-a934402d0baf\",\n"
                + "  \"entry_point\": \"@container::GE_simple_PI_controller::GE_simple_PI_controller::GE_simple_PI_controller.main\",\n"
                + "  \"timestamp\": \"2021-05-14\",\n"
                + "  \"hyper_edges\": [\n"
                + "    {\n"
                + "      \"inputs\": [\n"
                + "        \"f728b4fa-4248-5e3a-0a5d-2f346baa9455\",\n"
                + "        \"eb1167b3-67a9-c378-7c65-c1e582e2e662\"\n"
                + "      ],\n"
                + "      \"function\": \"ba26d851-35e8-579a-7aaf-0e891fb797fa\",\n"
                + "      \"outputs\": [\n"
                + "        \"1846d424-c17c-6279-23c6-612f48268673\"\n"
                + "      ]\n"
                + "    }\n"
                + "  ],\n"
                + "  \"variables\": [\n"
                + "    {\n"
                + "      \"uid\": \"f728b4fa-4248-5e3a-0a5d-2f346baa9455\",\n"
                + "      \"identifier\": \"GE_simple_PI_controller::GE_simple_PI_controller.PI_calc::Input_dmd::-1\",\n"
                + "      \"object_ref\": null,\n"
                + "      \"metadata\": [\n"
                + "        {\n"
                + "          \"type\": \"domain\",\n"
                + "          \"provenance\": {\n"
                + "            \"method\": \"program_analysis_pipeline\",\n"
                + "            \"timestamp\": \"2021-05-14 16:28:20.565330\"\n"
                + "          },\n"
                + "          \"data_type\": \"integer\",\n"
                + "          \"measurement_scale\": \"discrete\",\n"
                + "          \"elements\": []\n"
                + "        },\n"
                + "        {\n"
                + "          \"type\": \"code_span_reference\",\n"
                + "          \"provenance\": {\n"
                + "            \"method\": \"program_analysis_pipeline\",\n"
                + "            \"timestamp\": \"2021-05-14 16:28:20.563284\"\n"
                + "          },\n"
                + "          \"code_type\": \"identifier\",\n"
                + "          \"code_file_reference_uid\": \"\",\n"
                + "          \"code_span\": {\n"
                + "            \"line_begin\": 8,\n"
                + "            \"line_end\": null,\n"
                + "            \"col_begin\": 23,\n"
                + "            \"col_end\": null\n"
                + "          }\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  ],\n"
                + "  \"functions\": [\n"
                + "    {\n"
                + "      \"uid\": \"ba26d851-35e8-579a-7aaf-0e891fb797fa\",\n"
                + "      \"type\": \"ASSIGN\",\n"
                + "      \"lambda\": \"lambda Input_dmd,Input_sensed: (Input_dmd - Input_sensed)\",\n"
                + "      \"metadata\": [\n"
                + "        {\n"
                + "          \"type\": \"code_span_reference\",\n"
                + "          \"provenance\": {\n"
                + "            \"method\": \"program_analysis_pipeline\",\n"
                + "            \"timestamp\": \"2021-05-14 16:28:20.564182\"\n"
                + "          },\n"
                + "          \"code_type\": \"block\",\n"
                + "          \"code_file_reference_uid\": \"\",\n"
                + "          \"code_span\": {\n"
                + "            \"line_begin\": 10,\n"
                + "            \"line_end\": null,\n"
                + "            \"col_begin\": 12,\n"
                + "            \"col_end\": null\n"
                + "          }\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  ],\n"
                + "  \"subgraphs\": [\n"
                + "    {\n"
                + "      \"uid\": \"31d0b664-0589-f877-9b02-52440950fd13\",\n"
                + "      \"namespace\": \"GE_simple_PI_controller\",\n"
                + "      \"scope\": \"GE_simple_PI_controller\",\n"
                + "      \"basename\": \"GE_simple_PI_controller.PI_calc\",\n"
                + "      \"occurrence_num\": 1,\n"
                + "      \"parent\": \"176ea1b1-6426-4cd5-1ea4-5cd69371a71f\",\n"
                + "      \"type\": \"FuncContainer\",\n"
                + "      \"border_color\": \"forestgreen\",\n"
                + "      \"nodes\": [\n"
//                + "        \"b7d6467b-2f5a-522a-f87f-43fdf6062541\",\n"
                + "        \"f728b4fa-4248-5e3a-0a5d-2f346baa9455\",\n"
//                + "        \"eb1167b3-67a9-c378-7c65-c1e582e2e662\",\n"
//                + "        \"f7c1bd87-4da5-e709-d471-3d60c8a70639\",\n"
//                + "        \"e443df78-9558-867f-5ba9-1faf7a024204\",\n"
//                + "        \"23a7711a-8133-2876-37eb-dcd9e87a1613\",\n"
//                + "        \"fcbd04c3-4021-2ef7-cca5-a5a19e4d6e3c\",\n"
//                + "        \"1846d424-c17c-6279-23c6-612f48268673\",\n"
                + "        \"ba26d851-35e8-579a-7aaf-0e891fb797fa\"\n"
//                + "        \"b4862b21-fb97-d435-8856-1712e8e5216a\",\n"
//                + "        \"ade9b2b4-efdd-35f8-0fa3-4266ccfdba9b\",\n"
//                + "        \"259f4329-e6f4-590b-9a16-4106cf6a659e\",\n"
//                + "        \"9edfa3da-6cf5-5b15-8b53-031d05d51433\",\n"
//                + "        \"11ebcd49-428a-1c22-d5fd-b76a19fbeb1d\"\n"
                + "      ],\n"
                + "      \"metadata\": [\n"
                + "        {\n"
                + "          \"type\": \"code_span_reference\",\n"
                + "          \"provenance\": {\n"
                + "            \"method\": \"program_analysis_pipeline\",\n"
                + "            \"timestamp\": \"2021-05-14 16:28:20.564255\"\n"
                + "          },\n"
                + "          \"code_type\": \"block\",\n"
                + "          \"code_file_reference_uid\": \"\",\n"
                + "          \"code_span\": {\n"
                + "            \"line_begin\": 9,\n"
                + "            \"line_end\": 15,\n"
                + "            \"col_begin\": null,\n"
                + "            \"col_end\": null\n"
                + "          }\n"
                + "        }\n"
                + "      ]\n"
                + "    }\n"
                + "  ]\n"
                + "}";
        String filename = "test_grfn.json";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", filename,
                MediaType.APPLICATION_JSON_VALUE, jsonContent.getBytes());

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/SemanticAnnotator/translate")
                                      .file(mockMultipartFile);

        String firstLine = "uri \"http://aske.ge.com/test_grfn\" alias test_grfn";
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(firstLine)));
    }

}
