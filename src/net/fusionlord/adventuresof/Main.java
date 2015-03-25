package net.fusionlord.adventuresof;

import net.fusionlord.adventuresof.game.Game;

import java.io.*;
//import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Author: FusionLord
 * Email: FusionLord@gmail.com
 */
public class Main
{

	private static File libs = new File("./libs/");
	private static File natives = new File("./natives/");

	public static void main(String[] args)
	{
		if (args.length > 0 && args[0].equalsIgnoreCase("-d"))
		{
			System.out.println("This could cause the app to not start remove the \"-d\" parameter to fix any crashes!");
		}
		else
		{
			if (!libs.exists())
			{
				extractLibs();
			}

			addLibs();

			if (!natives.exists())
			{
				extractNatives();
			}

			String os = System.getProperty("os.name").toLowerCase();
			if (os.contains("win"))
			{
				os = "windows";
			}
			if (os.contains("mac"))
			{
				os = "maxosx";
			}
			if (os.contains("nix") || os.contains("nux") || os.contains("aix"))
			{
				os = "linux";
			}
			if (os.contains("sunos"))
			{
				os = "solaris";
			}

			System.setProperty("org.lwjgl.librarypath", new File("natives/".concat(os)).getAbsolutePath());
		}
		try
		{
			Game.initialize(args);
		}
		catch (NoClassDefFoundError ex)
		{
			try
			{
				libs.delete();
				extractLibs();
				addLibs();
				Game.initialize(args);
			}
			catch (NoClassDefFoundError e)
			{
				System.out.println("There is a problem with the libraries, please report this with the log.");

				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	private static void extractLibs()
	{
		JarURLConnection jURL;
		try
		{
			jURL = (JarURLConnection) Main.class.getClassLoader().getResource("libs/").openConnection();
			copyJarResourceToFolder(jURL, libs);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void addLibs()
	{
		try
		{
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
			method.setAccessible(true);
			for (File file : libs.listFiles(
					new FileFilter()
					{
						@Override
						public boolean accept(File pathname)
						{
							return pathname.getName().toLowerCase().endsWith(
									".jar"
							);
						}
					}))
				method.invoke(ClassLoader.getSystemClassLoader(), file.toURI().toURL());
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	private static void extractNatives()
	{
		JarURLConnection jURL;
		try
		{
			jURL = (JarURLConnection) Main.class.getClassLoader().getResource("natives/").openConnection();
			copyJarResourceToFolder(jURL, natives);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void copyJarResourceToFolder(JarURLConnection jarConnection, File destDir)
	{

		try
		{
			JarFile jarFile = jarConnection.getJarFile();

			/**
			 * Iterate all entries in the jar file.
			 */
			for (Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements(); )
			{

				JarEntry jarEntry = e.nextElement();
				String jarEntryName = jarEntry.getName();
				String jarConnectionEntryName = jarConnection.getEntryName();

				/**
				 * Extract files only if they match the path.
				 */
				if (jarEntryName.startsWith(jarConnectionEntryName))
				{

					String filename = jarEntryName.startsWith(jarConnectionEntryName) ? jarEntryName.substring(
							jarConnectionEntryName.length()
					) : jarEntryName;
					File currentFile = new File(destDir, filename);

					if (jarEntry.isDirectory())
					{
						currentFile.mkdirs();
					}
					else
					{
						if (currentFile.exists())
						{
							System.out.println(currentFile.getName().concat(" already exists, skipping."));
							continue;
						}
						InputStream is = jarFile.getInputStream(jarEntry);
						OutputStream out = openOutputStream(currentFile);
						byte[] buffer = new byte[]{(byte) (1024 * 4)};
						int n;
						while ((n = is.read(buffer)) != -1)
						{
							out.write(buffer, 0, n);
						}
						is.close();
						out.close();
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static FileOutputStream openOutputStream(File file) throws IOException
	{

		if (file.exists())
		{
			if (file.isDirectory())
			{
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (!file.canWrite())
			{
				throw new IOException("File '" + file + "' cannot be written to");
			}
		}
		else
		{
			File parent = file.getParentFile();
			if (parent != null)
			{
				if (!parent.mkdirs() && !parent.isDirectory())
				{
					throw new IOException("Directory '" + parent + "' could not be created");
				}
			}
		}
		return new FileOutputStream(file, false);
	}
}
