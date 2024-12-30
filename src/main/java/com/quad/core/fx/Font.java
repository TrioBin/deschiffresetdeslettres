package com.quad.core.fx;

public class Font
{
	public final int NUM_UNICODES = 95;
	public int[] offsets = new int[NUM_UNICODES];
	public int[] widths = new int[NUM_UNICODES];
	public Image image;
	
	public Font(String name, String type, int size)
	{
		String path = "/fonts/" + name + "_" + type + "_" + size + ".png";

		try {
			image = new Image(path);
		} catch (Exception e) {
			System.err.println("Error loading font: " + path);
		}

		int unicode = -1;
	
		for(int x = 0; x < image.width; x++)
		{
			int color = image.pixels[x];
			
			if(color == 0xff0000ff)
			{
				unicode++;
				offsets[unicode] = x;
			}
			
			if(color == 0xffffff00)
			{
				widths[unicode] = x - offsets[unicode];
			}
		}
	}
}
