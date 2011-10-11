/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author leobint
 */
public class PackageReader {

    public static String[] getClassName(String packageName) throws ClassNotFoundException, IOException {
        //Class[] classes = getClasses(packageName);
//        Class[] classes = getClasseNamesInPackage(ApplicationConfiguration.load().fileJarForLoadClass, packageName);
//        String[] result = new String[classes.length];
//        for (int i = 0; i < classes.length; i++) {
//            Class class1 = classes[i];
//            String classFullName = class1.getCanonicalName();
//            result[i] = classFullName.substring(classFullName.lastIndexOf('.') + 1);
//        }
//        return result;
        return getClasseNamesInPackage(ApplicationConfiguration.load().fileJarForLoadClass, packageName);
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile().replace("%20", " ")));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
//                assert !file.getName().contains(".");
//                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static String[] getClasseNamesInPackage(String jarName, String packageName) {
        ArrayList<String> classes = new ArrayList<String>();

        packageName = packageName.replaceAll("\\.", "/");
        System.out.println("Jar " + jarName + " looking for " + packageName);
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream("lib/" + jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if ((jarEntry.getName().startsWith(packageName))
                        && (jarEntry.getName().endsWith(".class"))) {
                    System.out.println("Found " + jarEntry.getName().replaceAll("/", "\\."));
                    classes.add(jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot find jar file, please put right jar file to lib folder");
            e.printStackTrace();
        }

        String[] result = new String[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            String classFullName = classes.get(i);
            classFullName = classFullName.substring(0, classFullName.lastIndexOf('.'));
            result[i] = classFullName.substring(classFullName.lastIndexOf('.') + 1);
        }
        return result;
    }
}
