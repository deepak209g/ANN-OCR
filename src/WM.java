import java.io.Serializable;


public class WM implements Serializable{
	int from,to;
	private double arr[][];
	public double prevdelta[][];
	public WM(double arr[][],int from,int to){
		this.arr = arr;
		this.from = from;
		this.to = to;
	}
	public WM(int from, int to, int sizel, int sizer){
		this.from = from;
		this.to = to;
		this.arr = new double[sizel][sizer];
		this.prevdelta = new double[sizel][sizer];
		for(int i=1;i<sizel;i++){
			for(int j=0;j<sizer;j++){
				this.arr[i][j] = -0.5 + Math.random();
			}
		}
		for(int i=0;i<sizer;i++){
			this.arr[0][i] = 1;
		}
	}

	public double get(int a,int b){
		return arr[a][b];
	}
	public void set(int a, int b, double val){
		arr[a][b] = val;
	}
	
}	
