package net.osgiliath.helpers.manifest.transformer;

/*
 * #%L
 * net.osgiliath.helpers.manifest.transformer
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.plexus.util.IOUtil;
import org.osgi.framework.Constants;

public class ManifestBundleImportVersionUpdaterTransformer {
    // Configuration
    private Map<String, String> entriesVersionUpdates = new HashMap<String, String>();

    // Fields
    private boolean manifestDiscovered;
    private Manifest manifest;

    private static ManifestBundleImportVersionUpdaterTransformer instance;

    private static ManifestBundleImportVersionUpdaterTransformer getInstance() {
        if (instance == null) {
            instance = new ManifestBundleImportVersionUpdaterTransformer();
        }
        return instance;
    }

    public boolean canTransformResource(String resource) {
        if (JarFile.MANIFEST_NAME.equalsIgnoreCase(resource)) {
            return true;
        }

        return false;
    }

    public void processResource(InputStream is) throws IOException {
        // We just want to take the first manifest we come across as that's our
        // project's manifest. This is the behavior
        // now which is situational at best. Right now there is no context
        // passed in with the processing so we cannot
        // tell what artifact is being processed.
        if (!manifestDiscovered) {
            manifest = new Manifest(is);
            manifestDiscovered = true;
            IOUtil.close(is);
        }
    }

    // public boolean hasTransformedResource() {
    // return true;
    // }

    public void updateManifestImportsWithOverrides() throws IOException {
        // If we didn't find a manifest, then let's create one.
        if (manifest == null) {
            System.out.println("New Manifest");
            manifest = new Manifest();
        }

        Attributes attributes = manifest.getMainAttributes();
        StringBuilder updatedImports = new StringBuilder();

        if (entriesVersionUpdates != null) {
            String imports = attributes.getValue(Constants.IMPORT_PACKAGE);
            StringBuilder regexp = new StringBuilder();
            regexp.append("([a-zA-Z0-9\\.]+?)");//symbolicname
            regexp.append("((;[^=]*?=([^;,]|,\\s*[\\.\\]\\)0-9]*\")*?)*?)");//specialization
            regexp.append("(,(?!\\s*[\\.\\]\\)0-9]*\")|$)");//iterator
            Pattern pattern = Pattern
                    .compile(regexp.toString());// ([a-z|\\.]+?)((;\\w*?=\".*?\")*)(,|$)
            Matcher matcher = pattern.matcher(imports);
            while (matcher.find()) {
                String symbolicName = matcher.group(1);
                Boolean foundMatch = Boolean.FALSE;
                updatedImports.append(symbolicName);
                for (Iterator i = entriesVersionUpdates.keySet().iterator(); i
                        .hasNext();) {
                    String key = (String) i.next();
                    if (key.equals(symbolicName)) {
                        String specialization = matcher.group(2);
                        if (specialization != null) {
                            foundMatch = Boolean.TRUE;
                            System.out.println("Replacement matched: "
                                    + symbolicName + "," + specialization);
                            specialization = specialization.replaceAll(
                                    ";\\s*version\\s*=\\s*\".*?\"",
                                    ";version=\""
                                            + entriesVersionUpdates.get(key)
                                            + "\"");
                            updatedImports.append(specialization);
                        }
                    }
                }
                if (!foundMatch) {
                    String specialization = matcher.group(2);
                    updatedImports.append(specialization);

                }
                updatedImports.append(",");

            }

        }
        attributes.put(
                new Attributes.Name(Constants.IMPORT_PACKAGE),
                updatedImports.toString().substring(0,
                        updatedImports.toString().length() - 1));

    }

    public static void main(String[] args) throws IOException {
        String[] overrides = null;
        String basedir = null;
        String bundleClasspath = null;
        for (String arg : args) {
            if (arg.startsWith("overrides=")) {
                overrides = arg.replaceFirst("overrides=", "").split(";");
            } else if (arg.startsWith("basedir=")) {
                basedir = arg.replaceFirst("basedir=", "");
            } else if (arg.startsWith("bundleClasspath=")) {
                bundleClasspath = arg.replaceFirst("bundleClasspath=", "");
            }
            System.out.println("Argument: " + arg);

        }
        InputStream is;

        try {
            is = new FileInputStream(basedir
                    + "/src/main/resources/META-INF/MANIFEST.MF");
            getInstance().processResource(is);
            is.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        getInstance().createOverrideMap(overrides);
        getInstance().updateManifestImportsWithOverrides();
        getInstance().addBundleClassPath(bundleClasspath);
        getInstance().writeManifest(basedir);
    }

    private void writeManifest(String basedir) throws IOException {
        OutputStream os;
        try {
            os = new FileOutputStream(basedir
                    + "/src/main/resources/META-INF/MANIFEST.MF");
            manifest.write(os);
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void addBundleClassPath(String bundleClasspath) {
        Attributes attributes = manifest.getMainAttributes();
        attributes.put(new Attributes.Name(Constants.BUNDLE_CLASSPATH), "., "
                + bundleClasspath + ".jar");
    }

    private void createOverrideMap(String[] overrides) {
        for (String override : overrides) {
            String[] mapEntry = override.split("=");
            if (mapEntry.length == 2) {
                entriesVersionUpdates.put(mapEntry[0], mapEntry[1]);
            }
        }
    }
}