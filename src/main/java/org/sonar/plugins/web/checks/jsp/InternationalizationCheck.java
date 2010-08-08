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

package org.sonar.plugins.web.checks.jsp;

import org.sonar.check.Check;
import org.sonar.check.CheckProperty;
import org.sonar.check.IsoCategory;
import org.sonar.check.Priority;
import org.sonar.plugins.web.checks.AbstractPageCheck;
import org.sonar.plugins.web.checks.Utils;
import org.sonar.plugins.web.node.TagNode;

/**
 * Checker to find hardcoded labels and messages.
 * 
 * @author Matthijs Galesloot
 * @since 1.0
 */
@Check(key = "InternationalizationCheck", title = "Labels Internationalization", 
    description = "Labels should be defined in the resource bundle", priority = Priority.MINOR, isoCategory = IsoCategory.Maintainability)
public class InternationalizationCheck extends AbstractPageCheck {

  @CheckProperty(key = "attributes", title="Attributes", description = "Attributes")
  private QualifiedAttribute[] attributes;

  public String getAttributes() {
    return getAttributesAsString(attributes);
  }

  public void setAttributes(String qualifiedAttributes) {
    this.attributes = parseAttributes(qualifiedAttributes);
  }

  @Override
  public void startElement(TagNode element) {
    if (attributes != null) {
      for (QualifiedAttribute attribute : attributes) {
        if (attribute.getNodeName().equals(element.getLocalName())) {
          String value = element.getAttribute(attribute.getAttributeName());
          if (value != null) {
            value = value.trim();
            if (value.length() > 0 && !Utils.isUnifiedExpression(value)) {
              createViolation(element);
              return;
            }
          }
        }
      }
    }
  }
}