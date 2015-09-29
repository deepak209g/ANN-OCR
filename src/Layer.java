import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 */

/**
 * @author 209de
 *
 */
public class Layer implements Serializable{
	int layerId;
	int len;
	double neurons[];
	double delta[];
	public Layer(double arr[]){
		this.neurons = arr;
		this.delta = new double[arr.length];
		Arrays.fill(delta, 0);
		this.len = arr.length;		
	}
	public Layer(int size){
		this.neurons = new double[size];
		this.len = size;
		this.delta = new double[size];
		Arrays.fill(this.delta, 0);
	}
	public void PrintDelta(){
		for(int i=0;i<len;i++){
			System.out.printf("%.02f ",delta[i]);
		}
		System.out.println();
	}
}
