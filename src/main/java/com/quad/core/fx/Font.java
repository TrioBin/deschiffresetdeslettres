package com.quad.core.fx;

public enum Font
{
	ARIAL("Arial"),
	COURIERNEW("Courier New"),
	STANDARD2("/fonts/standard2.png"),
	SANSSERIF50("/fonts/sansserif50.png"),

	NORMAL("normal"),
	BOLD("bold"),
	ITALIC("italic");
	
	public final int NUM_UNICODES = 59;
	public int[] offsets = new int[NUM_UNICODES];
	public int[] widths = new int[NUM_UNICODES];
	public Image image;
	
	Font(String path)
	{
		image = new Image(path);
		
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
