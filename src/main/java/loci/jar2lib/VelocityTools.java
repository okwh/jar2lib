//
// VelocityTools.java
//

/*
Jar2Lib tool for generating C++ proxy classes for a Java library.

Copyright (c) 2010, UW-Madison LOCI
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the UW-Madison LOCI nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/

package loci.jar2lib;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * Useful methods for working with Apache Velocity.
 *
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="http://dev.loci.wisc.edu/trac/java/browser/trunk/projects/jar2lib/src/main/java/loci/jar2lib/VelocityTools.java">Trac</a>,
 * <a href="http://dev.loci.wisc.edu/svn/java/trunk/projects/jar2lib/src/main/java/loci/jar2lib/VelocityTools.java">SVN</a></dd></dl>
 *
 * @author Curtis Rueden ctrueden at wisc.edu
 */
public class VelocityTools {

  public static VelocityEngine createEngine() throws VelocityException {
    // initialize Velocity engine; enable loading of templates as resources
    VelocityEngine ve = new VelocityEngine();
    Properties p = new Properties();
    p.setProperty("resource.loader", "class");
    p.setProperty("class.resource.loader.class",
      "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    try {
      ve.init(p);
    }
    // NB: VelocityEngine.init(Properties) throws Exception.
    catch (Exception exc) {
      throw new VelocityException(exc);
    }
    return ve;
  }

  public static VelocityContext createContext() {
    // populate Velocity context
    VelocityContext context = new VelocityContext();
    context.put("user", System.getProperty("user.name"));
    DateFormat dateFmt = DateFormat.getDateInstance(DateFormat.MEDIUM);
    DateFormat timeFmt = DateFormat.getTimeInstance(DateFormat.LONG);
    Date date = Calendar.getInstance().getTime();
    context.put("timestamp", dateFmt.format(date) + " " + timeFmt.format(date));

    return context;
  }

  public static void processTemplate(VelocityEngine ve,
    VelocityContext context, String inFile, String outFile)
    throws VelocityException, IOException
  {
    System.out.print("Writing " + outFile + ": ");
    final Template t;
    try {
      t = ve.getTemplate(inFile);
    }
    // NB: VelocityEngine.getTemplate(String) throws Exception.
    catch (Exception exc) {
      throw new VelocityException(exc);
    }
    final StringWriter writer = new StringWriter();
    t.merge(context, writer);
    final PrintWriter out = new PrintWriter(new FileWriter(outFile));
    out.print(writer.toString());
    out.close();
    System.out.println("done.");
  }

}
