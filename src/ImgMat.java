


public class ImgMat extends Mat{

	public ImgMat(double[][] arr) {
		super(arr);
		// TODO Auto-generated constructor stub
	}

	public ImgMat(int n, int m) {
		super(n, m);
	}
	
	public void GetRegionofInterest(){
		double arr[][] = this.arr;
		int left=-1,right=-1,top=-1,bottom=-1;
		for(int i=0;i<this.n;i++){
			for(int j=0;j<this.m;j++){
				if(arr[i][j]>0 && top == -1){
					top = i;
				}
				if(arr[n-i-1][j]>0 && bottom == -1){
					bottom = n-i-1;
				}
			}
		}
		for(int i=0;i<this.m;i++){
			for(int j=0;j<this.n;j++){
				if(arr[j][i]>0 && left == -1){
					left = i;
				}
				if(arr[j][m-i-1]>0 && right == -1){
					right = m-i-1;
				}
			}
		}
		
		if(top == -1){
			top = 0;
		}
		if(left == -1){
			left = 0;
		}
		if(bottom == -1){
			bottom = n-1;
		}
		if(right == -1){
			right = m-1;
		}
		
		
		double temp[][] = new double[bottom-top+1][right - left +1];
		for(int i = top ; i <=bottom ; i++){
			for(int j = left ; j <= right; j++){
				temp[i-top][j-left] = arr[i][j];
			}
		}
		this.n = bottom - top + 1;
		this.m = right - left + 1;
		this.arr = temp;
	}
	
	public void ScaleDown(int wid,int hei){
		double scaleW = m/(double)wid;
		double scaleH = n/(double)hei;
		double px,py;
		double retarr[][] = new double[hei][wid];
		for(int i=0;i<hei;i++){
			for(int j=0;j<wid;j++){
				px = Math.floor(j*scaleW);
				py = Math.floor(i*scaleH);
				retarr[i][j] = this.arr[(int)py][(int)px]; 
			}
		}

		
		this.n = hei;
		this.m = wid;
		this.arr = retarr;
	}
	
	

}
