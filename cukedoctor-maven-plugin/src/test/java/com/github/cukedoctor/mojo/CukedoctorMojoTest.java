package com.github.cukedoctor.mojo;

import com.github.cukedoctor.util.FileUtil;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static com.github.cukedoctor.extension.CukedoctorExtensionRegistry.FILTER_DISABLE_EXT_KEY;
import static com.github.cukedoctor.mojo.FileUtil.loadTestFile;
import static com.github.cukedoctor.mojo.FileUtil.readFileContent;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pestano on 27/06/15.
 */
public class CukedoctorMojoTest extends AbstractMojoTestCase {



    protected void setUp() throws Exception {
        super.setUp();
        System.clearProperty("cukedoctor.disable.theme");
        System.clearProperty(FILTER_DISABLE_EXT_KEY);
        System.clearProperty("cukedoctor.disable.minmax");
        System.clearProperty("cukedoctor.disable.footer");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        System.clearProperty("cukedoctor.disable.theme");
        System.clearProperty(FILTER_DISABLE_EXT_KEY);
        System.clearProperty("cukedoctor.disable.minmax");
        System.clearProperty("cukedoctor.disable.footer");

    }

    /**
     * @throws Exception
     */
    public void testGenerateHtmlDocs() throws Exception {

        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/html-docs-pom.xml"));

        assertNotNull(mojo);
        mojo.execute();
        File file = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".html");
        assertThat(file).exists().hasParent("target/docs");
        assertThat(mojo.getGeneratedFile()).
                contains(":backend: html5").
                contains(":toc: left").
                contains(":numbered:");
        String docHtml = readFileContent(loadTestFile("documentation.html"));
        assertThat(docHtml).isNotEmpty().
                containsOnlyOnce("searchFeature(criteria)").
                containsOnlyOnce("function showFeatureScenarios(featureId)").
                contains("One passing scenario, one failing scenario").
                contains("Eat cukes in lot").
                containsOnlyOnce("function themefy()");
    }

    /**
     * @throws Exception
     */
    public void testGenerateHtmlDocsWithoutFilterExtension() throws Exception {

        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/html-docs-no-filter-pom.xml"));

        assertNotNull(mojo);
        mojo.execute();
        File file = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".html");
        assertThat(file).exists().hasParent("target/docs");
        assertThat(mojo.getGeneratedFile()).
                contains(":backend: html5").
                contains(":toc: left").
                contains(":numbered:");
        String docHtml = readFileContent(loadTestFile("documentation.html"));
        assertThat(docHtml).isNotEmpty().
                doesNotContain("searchFeature(criteria)").
                containsOnlyOnce("function showFeatureScenarios(featureId)").
                contains("One passing scenario, one failing scenario").
                contains("Eat cukes in lot").
                containsOnlyOnce("function themefy()");
    }

    /**
     * @throws Exception
     */
    public void testGenerateHtmlDocsWithoutThemeExtension() throws Exception {

        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/html-docs-no-theme-pom.xml"));

        assertNotNull(mojo);
        mojo.execute();
        File file = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".html");
        assertThat(file).exists().hasParent("target/docs");
        assertThat(mojo.getGeneratedFile()).
                contains(":backend: html5").
                contains(":toc: left").
                contains(":numbered:");

        String docHtml = readFileContent(loadTestFile("documentation.html"));
        assertThat(docHtml).isNotEmpty().
                containsOnlyOnce("searchFeature(criteria)").
                containsOnlyOnce("function showFeatureScenarios(featureId)").
                containsOnlyOnce("Generated by").
                containsOnlyOnce("Execution time:").
                contains("One passing scenario, one failing scenario").
                contains("Eat cukes in lot").
                doesNotContain("function themefy()").
                doesNotContain("<div name=\"themes\" id=\"themes\"");
    }

  /**
   * @throws Exception
   */
  public void testGenerateHtmlDocsWithoutFooterExtension() throws Exception {

    CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/html-docs-no-footer-pom.xml"));

    assertNotNull(mojo);
    mojo.execute();
    File file = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".html");
    assertThat(file).exists().hasParent("target/docs");
    assertThat(mojo.getGeneratedFile()).
        contains(":backend: html5").
        contains(":toc: left").
        contains(":numbered:");
    String docHtml = readFileContent(loadTestFile("documentation.html"));
    assertThat(docHtml).isNotEmpty().
        containsOnlyOnce("searchFeature(criteria)").
        containsOnlyOnce("function showFeatureScenarios(featureId)").
        containsOnlyOnce("function themefy()").
        containsOnlyOnce("<div name=\"themes\" id=\"themes\"").
        doesNotContain("Generated by").
        doesNotContain("Execution time:");
  }

  /**
   * @throws Exception
     */
    public void testGenerateHtmlDocsWithoutExtensions() throws Exception {

        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/html-docs-no-extension-pom.xml"));

        assertNotNull(mojo);
        mojo.execute();
        File file = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".html");
        assertThat(file).exists().hasParent("target/docs");
        assertThat(mojo.getGeneratedFile()).
                contains(":backend: html5").
                contains(":toc: left").
                contains(":numbered:");
        String docHtml = readFileContent(loadTestFile("documentation.html"));
        assertThat(docHtml).isNotEmpty().
                doesNotContain("searchFeature(criteria)").
                doesNotContain("function showFeatureScenarios(featureId)").
                doesNotContain("function themefy()").
                doesNotContain("Generated by").
                doesNotContain("Execution time:");
    }


    /**
     * @throws Exception
     */
    public void testGeneratePdfDocs() throws Exception {

        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/pdf-docs-pom.xml"));

        assertNotNull(mojo);
        mojo.execute();
        File file = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".pdf");
        assertThat(file).exists().hasParent("target/cukedoctor");
        assertThat(mojo.getGeneratedFile()).
                contains(":toc: right").
                contains(":backend: pdf");
    }

    /**
     * @throws Exception
     */
    public void testGenerateHtmlDocsUsingFeaturesDir() throws Exception {

        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/html-docs-with-features-dir-pom.xml"));

        assertNotNull(mojo);
        mojo.execute();
        File file = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".html");
        assertThat(file).exists().hasParent("target/docs");
        assertThat(mojo.getGeneratedFile()).
                contains(":backend: html5").
                contains(":toc: left").
                contains(":numbered:").
                contains("One passing scenario, one failing scenario").
                doesNotContain("Eat cukes in lot");//eat-cukes.json is not in featuresDir folder
        String docHtml = readFileContent(loadTestFile("documentation.html"));
        assertThat(docHtml).isNotEmpty().
                containsOnlyOnce("searchFeature(criteria)").
                containsOnlyOnce("function showFeatureScenarios(featureId)").
                contains("One passing scenario, one failing scenario").
                doesNotContain("Eat cukes in lot").
                containsOnlyOnce("function themefy()");
    }

    /**
     * @throws Exception
     */
    public void testGenerateAllDocs() throws Exception {

        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/html-pdf-docs-pom.xml"));

        assertNotNull(mojo);
        mojo.execute();
        File pdfFile = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".pdf");
        assertThat(pdfFile).exists().hasParent("target/docs");

        String docHtml = readFileContent(loadTestFile("documentation.html"));
        assertThat(docHtml).isNotEmpty().
                containsOnlyOnce("searchFeature(criteria)").
                containsOnlyOnce("function showFeatureScenarios(featureId)").
                contains("One passing scenario, one failing scenario").
                contains("Eat cukes in lot").
                contains("function themefy()");
        File htmlFile = FileUtil.loadFile(mojo.getDocumentationDir() + mojo.outputFileName + ".html");
        assertThat(htmlFile).exists().hasParent("target/docs");

    }

    public void testSkipDocsGenerationTest() throws Exception {
        CukedoctorMojo mojo = (CukedoctorMojo) lookupMojo("execute", getTestFile("src/test/resources/skip-docs-pom.xml"));
        assertNotNull(mojo);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        mojo.execute();
        assertThat(outContent.toString()).contains("Skipping cukedoctor-maven-plugin");

    }

}
