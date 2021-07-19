package com.ge.research.sadl.darpa.aske.rest.grasen.service;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Checks that the application's REST endpoint can receive a JSON file
 * and will return a SADL file generated from the JSON file.
 *
 * Also checks that the application's REST endpoint has Swagger UI
 * documentation.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class GrasenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldDisplaySwaggerUiPage() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Swagger")));
    }

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
                + "          \"type\": \"from_source\",\n"
                + "          \"provenance\": {\n"
                + "             \"method\": \"program_analysis_pipeline\",\n"
                + "             \"timestamp\": \"2021-06-09 09:29:08.671742\"\n"
                + "          },\n"
                + "          \"from_source\": \"True\",\n"
                + "          \"creation_reason\": \"UNKNOWN\"\n"
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
                + "        \"f728b4fa-4248-5e3a-0a5d-2f346baa9455\",\n"
                + "        \"ba26d851-35e8-579a-7aaf-0e891fb797fa\"\n"
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
