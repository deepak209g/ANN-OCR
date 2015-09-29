import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class Network implements Serializable{
	ArrayList<Layer> graph;
	ArrayList<WM> wgraph;
	double LearningRate,momentum;
	public Network(){
		this.graph = new ArrayList<Layer>();
		this.wgraph = new ArrayList<WM>();
		this.LearningRate = 0.25;
		this.momentum = 0.1;
	}
	public void insertLayer(Layer l){
		this.graph.add(l);
	}
	
	public void finalise(){
		int a = this.graph.size();
		for(int i=0;i<a-1;i++){
			int sizeL = this.graph.get(i).len;
			int sizeR = this.graph.get(i+1).len;
			this.wgraph.add(new WM(i,i+1,sizeL+1,sizeR));
		}
	}

	public void Train(double inp[],double op[]){
		Layer input = this.graph.get(0);
		for(int i=0;i<inp.length;i++){
			input.neurons[i] = inp[i];
		}
		Feedforward();
		BackPropogate(op);
		UpdateWeights();
	}
	
	private void Feedforward(){
		for(int i=0;i<this.graph.size()-1;i++){
			int sl = this.graph.get(i).len;
			int sr = this.graph.get(i+1).len;
			Layer layerL = this.graph.get(i);
			Layer layerR = this.graph.get(i+1);
			WM temp = this.wgraph.get(i);
			double sum = 0;
			for(int j=0;j<sr;j++){
				sum = 0;
				for(int k=0;k<sl;k++){
					double weight = temp.get(k+1, j);
					double op1 = layerL.neurons[k];
					sum = sum + op1*weight;
				}
				sum += temp.get(0, j);
				double activation = sigmoid(sum);
				layerR.neurons[j] = activation;
			}
		}
	}

	private void BackPropogate(double op[]){
		Layer outputL = this.graph.get(this.graph.size()-1);
		double a[] = outputL.neurons;
		double delta[] = outputL.delta;
		for(int i=0;i<delta.length;i++){
			delta[i] = (a[i] - op[i])*a[i]*(1-a[i]);
		}
		
		outputL.delta = delta;
		for(int i=this.graph.size()-2;i>0;i--){
			int sl = this.graph.get(i).len;
			int sr = this.graph.get(i+1).len;
			Layer ll = this.graph.get(i);
			Layer lr = this.graph.get(i+1);
			WM temp = this.wgraph.get(i);
			for(int j=0;j<sl;j++){
				double delsum = 0;
				for(int k=0;k<sr;k++){
					delsum += temp.get(j+1, k)*lr.delta[k];
				}
				ll.delta[j] = delsum*ll.neurons[j]*(1 - ll.neurons[j]);
			}
		}
	}

	private void UpdateWeights(){
		for(int i=0;i<this.graph.size()-1;i++){
			int sl = this.graph.get(i).len;
			int sr = this.graph.get(i+1).len;
			Layer layerL = this.graph.get(i);
			Layer layerR = this.graph.get(i+1);
			WM temp = this.wgraph.get(i);
			for(int j=0;j<sr;j++){
				for(int k=0;k<sl;k++){
					double weight = temp.get(k+1, j);
					double delta = this.LearningRate*layerL.neurons[k]*layerR.delta[j];
					weight -= delta;
					temp.set(k+1, j, weight);
				}	
				double bias = temp.get(0, j);
				bias += this.LearningRate*layerR.delta[j];
				temp.set(0, j, bias);
			}
		}
	}

	public double Error(double inp[],double op[]){
		double act[] = Query(inp);
		double err=0.0;
		for(int i=0;i<op.length;i++){
			err += Math.pow((op[i]-act[i]),2);
		}
		if(Double.isNaN(err)){
			for(int i=0;i<op.length;i++){
				System.out.println("out: "+op[i] + " act: "+act[i]);
			}
			
		}
		return Math.sqrt(err);
	}
	
	public double[] Query(double inp[]){
		Layer input = this.graph.get(0);
		for(int i=0;i<inp.length;i++){
			input.neurons[i] = inp[i];
		}
		Feedforward();
		double oplayer[] = this.graph.get(this.graph.size()-1).neurons;
		return oplayer;
	}
	
	public static int[] boolOut(double[] inp){
		int maxat=0;
		int op[] = new int[inp.length];
		for(int i=0;i<inp.length;i++){
			if(inp[i]>inp[maxat]){
				maxat = i;
			}
			op[i]=0;
		}
		op[maxat] = 1;
		return op;
	}
	
	public double sigmoid(double a){
		return 1.0/(1.0 + Math.exp(-a)); 
	}
}
