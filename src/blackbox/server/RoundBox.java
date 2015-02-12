package blackbox.server;
class RoundBox {
	private int gap;
	public int top,left,right,bottom;
	private int rx,ry;
	private int binding;
	RoundBox(int Gap, int rx, int ry) {
		this.gap = Gap;
		this.rx = rx;
		this.ry = ry;
		top=left = 0;
		right=bottom = 0;
	}
	public boolean isInside(float x, float y) {
		if(x >= top && x < right && y >= left && y < bottom)
			return true;
		return false;
	}
	public RoundBox setDimension(int top, int left, int right, int bottom){
		this.top = top;
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		return this;
	}
	public void setPadding(){
		int gap = this.gap/2;
		top += gap;
		left += gap;
		right -= gap;
		bottom -= gap;
	}
	public void setBinding(int keycode){
		binding = keycode;
	}
	public int getBinding(){
		return binding;
	}
	public static void main(String[] args) {
		RoundBox b = new RoundBox(10, 10, 10);
		b.setDimension(0, 0, 1280/2, 768/2);
		System.out.println(b.top +","+b.right);
		System.out.println(b.isInside(34, 50));
	}
	
}