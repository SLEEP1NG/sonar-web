/*
 * Copyright (C) 2010 Matthijs Galesloot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sonar.plugins.web.maven;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.sonar.plugins.web.Settings;
import org.sonar.plugins.web.html.HtmlValidator;
import org.sonar.plugins.web.toetstool.ToetsTool;
import org.sonar.plugins.web.toetstool.ToetsToolReportBuilder;

/**
 * Goal to execute the verification with Toetstool.
 *
 * @goal validate-html-toetstool
 */
public class ToetstoolMojo extends AbstractValidationMojo {

  /**
   * Toetstool URL.
   *
   * @parameter
   * @required
   */
  private String toetsToolUrl;

  public void execute() throws MojoExecutionException {

    configureSettings();

    // prepare HTML
    prepareHtml();

    // execute validation
    File htmlFolder = new File(Settings.getHtmlDir());
    HtmlValidator toetstool = new ToetsTool();
    toetstool.validateFiles(htmlFolder);

    // build report
    ToetsToolReportBuilder reportBuilder = new ToetsToolReportBuilder();
    reportBuilder.buildReports(htmlFolder);
  }

  @Override
  protected void configureSettings() {
    super.configureSettings();

    getLog().info("toetsToolUrl = " + toetsToolUrl);
    Settings.setToetstoolURL(toetsToolUrl);
  }
}