/**********************************************************************
 * Note: This license has also been called the "New BSD License" or
 * "Modified BSD License". See also the 2-clause BSD License.
 *
 * Copyright � 2020-2021 - General Electric Company, All Rights Reserved
 *
 * Projects: GraSEN, developed with the support of the Defense Advanced
 * Research Projects Agency (DARPA) under Agreement  No. HR.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 *
 ***********************************************************************/
package com.ge.research.sadl.darpa.aske.rest.grasen.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ge.research.sadl.darpa.aske.grfnmodelextractor.GrFNModelExtractor;
import com.ge.research.sadl.owl2sadl.OwlImportException;
import com.ge.research.sadl.reasoner.AmbiguousNameException;
import com.ge.research.sadl.reasoner.ConfigurationException;
import com.ge.research.sadl.reasoner.InvalidNameException;
import com.ge.research.sadl.reasoner.QueryCancelledException;
import com.ge.research.sadl.reasoner.QueryParseException;
import com.ge.research.sadl.reasoner.ReasonerNotFoundException;
import com.ge.research.sadl.reasoner.TranslationException;

/**
 * Receives an uploaded JSON file, translates it from JSON to OWL and
 * SADL, and returns the SADL file.  Keep in mind the REST interface
 * is experimental; a lot of things are still hardcoded at this time.
 *
 * <br>The REST interface currently accepts only one file uploaded to
 * the service and doesn't look for any other parameters in the POST
 * request.  You may want to change the interface to pass more
 * parameters to the service so you can specify the name of the
 * returned SADL file, etc.
 *
 * <br>Also note that the code expects to be run from the project's
 * root directory with a fully built target subdirectory at this time.
 * You will need to improve the code if you want to copy the
 * application jar to any place you want and run the application on
 * any host you want.  You will have to move the files used by the
 * application from src/test/resources to src/main/resources and make
 * the application copy these resources to a temporary directory each
 * time it runs.
 */
@Controller
@RequestMapping("/SemanticAnalysis")
public class GrasenController {

    private static final Logger logger = LoggerFactory.getLogger(GrasenController.class);

    private String grFNExtractionProjectModelFolder;

    private String modelsFolderName = "SemanticModels";

    private String tempFolderName = "SATempDir";

    public GrasenController() throws IOException {
        copyFilesToTmpFolder(modelsFolderName, tempFolderName);

        File extractionProjectModelFolder = new File(tempFolderName);
        grFNExtractionProjectModelFolder = extractionProjectModelFolder.getCanonicalPath();
    }

//    @Operation(summary = "Receives a GrFN+ExpressionTree JSON file and returns a Semantic Analysis model in SADL")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Semantic Analysis done",
//                         content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
//            @ApiResponse(responseCode = "404", description = "Error in Semantic Analysis",
//                         content = @Content) })
//    @PostMapping(value = "/semanticAnalysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseBody
//    public ResponseEntity<Resource> translate(@RequestParam("file") MultipartFile file)
//        throws ConfigurationException, FileNotFoundException, IOException, ReasonerNotFoundException, TranslationException, QueryParseException, QueryCancelledException, InvalidNameException, AmbiguousNameException, OwlImportException {
//
////        List<String> sadlFilenames = semanticAnalysis(file);
//        String sadlFilename = semanticAnalysis(file);
//        
//        for (String filename : sadlFilenames) {
//            Resource resource = loadAsResource(filename);
//            logger.info("Returning filename {}", resource.getFilename());
//            return ResponseEntity.ok()
//                .contentType(MediaType.TEXT_PLAIN)
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//        }
//
//        logger.error("No file returned after translation");
//        return ResponseEntity.notFound().build();
//    }

    @Operation(summary = "Receives a GrFN+ExpressionTree JSON file and returns a CSV with Semanic Analysis results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Semantic Analysis done",
                         content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "404", description = "Error in Semantic Analysis",
                         content = @Content) })
    @PostMapping(value = "/queryService", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> querySemAnalysis(@RequestParam("file") MultipartFile file)
        throws ConfigurationException, FileNotFoundException, IOException, ReasonerNotFoundException, TranslationException, QueryParseException, QueryCancelledException, InvalidNameException, AmbiguousNameException, OwlImportException {

        String sadlFilename = querySemanticAnalysis(file);

        if (sadlFilename == null) {
            logger.error("No file returned by Semantic Analysis");
            return ResponseEntity.notFound().build();
        }
        
        Resource resource = loadAsResource(sadlFilename);
        logger.info("Returning filename {}", resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

        
//        for (String filename : sadlFilenames) {
//            Resource resource = loadAsResource(filename);
//            logger.info("Returning filename {}", resource.getFilename());
//            return ResponseEntity.ok()
//                .contentType(MediaType.TEXT_PLAIN)
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//        }

    }

    
    
    @Operation(summary = "Receives a GrFN+ExpressionTree JSON file and returns a Semantic Analysis model in SADL and a CSV file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Semantic Analysis done",
                         content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "404", description = "Error in Semantic Analysis",
                         content = @Content) })
    @PostMapping(value = "/generateModel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> semanticAnalysisCSV(@RequestParam("file") MultipartFile file)
        throws ConfigurationException, FileNotFoundException, IOException, ReasonerNotFoundException, TranslationException, QueryParseException, QueryCancelledException, InvalidNameException, AmbiguousNameException, OwlImportException {

        String sadlFilename = semanticAnalysis(file);
        if (sadlFilename == null) {
            logger.error("No file returned by Semantic Analysis");
            return ResponseEntity.notFound().build();
        }
        
        Resource resource = loadAsResource(sadlFilename);
        logger.info("Returning filename {}", resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    
	private String semanticAnalysis(MultipartFile file) throws IOException, ConfigurationException,
			ReasonerNotFoundException, TranslationException, QueryParseException, QueryCancelledException,
			InvalidNameException, AmbiguousNameException, OwlImportException {
		
		logger.info("Calling semantic analysis with filename {}", file.getOriginalFilename());
        String grFNJsonContent = new String(file.getInputStream().readAllBytes());
        String defaultCodeModelPrefix = getModelPrefixFromInputFile(file);
        String defaultCodeModelName = "http://grasen.ge.com/" + defaultCodeModelPrefix;
        String outputOwlFileName = defaultCodeModelPrefix; // + ".owl"

        GrFNModelExtractor grfnExtractor = new GrFNModelExtractor();
        grfnExtractor.setOwlModelsFolder(grFNExtractionProjectModelFolder);
        grfnExtractor.setCodeModelPrefix(defaultCodeModelPrefix);
        grfnExtractor.setCodeModelName(defaultCodeModelName);

        grfnExtractor.process("some grfn file identifier", grFNJsonContent, defaultCodeModelName, defaultCodeModelPrefix, false);
        
        File of = grfnExtractor.saveAsOwlFile(outputOwlFileName);
        Map<File, Integer> outputOwlFilesBySourceType = new HashMap<File, Integer>();
        outputOwlFilesBySourceType.put(of, 2);
        
        List<String> sadlFilenames = grfnExtractor.saveAsSadlFile(outputOwlFilesBySourceType, "yes");

//        String csvFileName = grfnExtractor.writeResultsToFile(file.getOriginalFilename().replace("json", "csv"));
//        sadlFilenames.add(csvFileName);
		return sadlFilenames.get(0);
	}

	private String querySemanticAnalysis(MultipartFile file) throws IOException, ConfigurationException,
	ReasonerNotFoundException, TranslationException, QueryParseException, QueryCancelledException,
	InvalidNameException, AmbiguousNameException, OwlImportException {

		logger.info("Calling semantic analysis with filename {}", file.getOriginalFilename());
		String grFNJsonContent = new String(file.getInputStream().readAllBytes());
		String defaultCodeModelPrefix = getModelPrefixFromInputFile(file);
		String defaultCodeModelName = "http://grasen.ge.com/" + defaultCodeModelPrefix;
//		String outputOwlFileName = defaultCodeModelPrefix + ".owl";
		
		GrFNModelExtractor grfnExtractor = new GrFNModelExtractor();
		grfnExtractor.setOwlModelsFolder(grFNExtractionProjectModelFolder);
		grfnExtractor.setCodeModelPrefix(defaultCodeModelPrefix);
		grfnExtractor.setCodeModelName(defaultCodeModelName);
		
		grfnExtractor.process("some grfn file identifier", grFNJsonContent, defaultCodeModelName, defaultCodeModelPrefix, true);
		
//		File of = grfnExtractor.saveAsOwlFile(outputOwlFileName);
//		Map<File, Integer> outputOwlFilesBySourceType = new HashMap<File, Integer>();
//		outputOwlFilesBySourceType.put(of, 2);
		
//		List<String> sadlFilenames = grfnExtractor.saveAsSadlFile(outputOwlFilesBySourceType, "yes");
		
		String csvFileName = grfnExtractor.writeResultsToFile(file.getOriginalFilename().replace("json", "csv"));
//		sadlFilenames.add(csvFileName);
//		return sadlFilenames;
		return csvFileName;
	}
	
    private Resource loadAsResource(String filename) throws FileNotFoundException {
        try {
            Path file = Paths.get(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename);
        }
    }

    /**
     * Method to generate a unique model prefix from the input File to the import
     * @param inputFile
     * @return
     */
    public String getModelPrefixFromInputFile(MultipartFile inputFile) {
        String prefix;
        String inputFilename = inputFile.getOriginalFilename();
        if (inputFilename.endsWith(".json")) {
            prefix = inputFilename.substring(0, inputFilename.length() - 5);
        }
        else {
            prefix = inputFilename.replaceAll("\\.", "_");
        }
        return prefix;
    }

    /**
     * Copy all files from jar folder to a temporary folder.
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void copyFilesToTmpFolder(String sourceFolder, String tmpFolderName) throws IOException, FileNotFoundException {
        File tempFolder = new File(tmpFolderName);
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        ClassLoader cl = this.getClass().getClassLoader();
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] files = resourcePatternResolver.getResources(sourceFolder + File.separator + "*");

        for(Resource fileRes : files) {
            logger.info("Copying file {} to temp folder", fileRes.getFilename());

            InputStream inStream = fileRes.getInputStream();
            FileOutputStream outStream = new FileOutputStream(new File(tempFolder, fileRes.getFilename()));
            byte[] buffer = new byte[inStream.available()];
            inStream.read(buffer);
            outStream.write(buffer);
        }
    }

}
