package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class outs
{
	public static void write(String s)
	{
		String path = "C:\\Users\\MARCO\\Desktop\\" + outs.getDate() + ".txt";
		
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(path);
		}
		catch (IOException e)
		{
			System.out.println("Impossibile... qualcosa con il writer? ...forse??");
			e.printStackTrace();
		}
		BufferedWriter buffer = new BufferedWriter(writer);

		try
		{
			buffer.write(s);
		}
		catch (IOException e)
		{
			System.out.println("Impossibile SCRIVERE CON IL WRITER... ??");
			e.printStackTrace();
		}
		
		try
		{
			buffer.close();
		}
		catch (IOException e)
		{
			System.out.println("Impossibile chiudere il BUFFER... ??");
			e.printStackTrace();
		}
		
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			System.out.println("Impossibile chiudere il WRITER... ??");
			e.printStackTrace();
		}
		
	}

	
	private static String getDate()
	{
		String timeStamp = new SimpleDateFormat("HHmmssyyMMdd").format(new Date());
		return "h"+ timeStamp.substring(0, 2)
				+"m"+ timeStamp.substring(2, 4)
					+"s"+ timeStamp.substring(4, 6)
						+" " //A little space to order better
							+"d"+ timeStamp.substring(6, 8)
								+"M"+ timeStamp.substring(8, 10)
									+"y"+ timeStamp.substring(10, 12);
	}
}
