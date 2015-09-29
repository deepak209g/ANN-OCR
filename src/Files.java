import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;




public class Files {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File actual = new File("assets/");
		File input;
		Network nn = null;
		try {
			
        	input = new File("networkk.ser");
        	if(input.createNewFile()){
        		System.out.println("Created fresh network!!");
        		nn = new Network();
        		nn.insertLayer(new Layer(360));
        		nn.insertLayer(new Layer(200));
        		nn.insertLayer(new Layer(26));
        		nn.finalise();
        	}else{
        		FileInputStream fileIn = new FileInputStream(input);
        		ObjectInputStream in = new ObjectInputStream(fileIn);
        		nn = (Network)in.readObject();
        		in.close();
        	}
        	
        	
    		double teacher[] = new double[26];
    		Arrays.fill(teacher, 0.0);
    		int train = 200000;
    		boolean ret = true;
    		File dirlist[] = actual.listFiles();
    		int a=0;
    		while(train>0){
    			a++;
    			a = a%26;
    			teacher[a] = 1;
    			File currdir = dirlist[a];
        		File imglist[] = currdir.listFiles();
        		File currentImage = imglist[(int)(10*Math.random())];
        		Image picturee = ImageIO.read(currentImage);
            	Image picture = picturee.getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING);
            	BufferedImage bi = new BufferedImage(picture.getWidth(null), picture.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
            	Graphics2D bGr =bi.createGraphics();
        	    bGr.drawImage(picture, 0, 0, null);
        	    bGr.dispose();
        	    
        	    double db[][] = new double[bi.getHeight(null)][bi.getHeight(null)];
        	    
        	    for(int i=0;i<bi.getHeight(null);i++){
        	    	for(int j=0;j<bi.getWidth(null);j++){
        	    		Color temp = new Color(bi.getRGB(j, i));
        	    		int col = temp.getBlue();
        	    		if(col>0){
        	    			col = 0;
        	    		}else{
        	    			col = 1;
        	    		}
        	    		db[i][j] = col;
        	    	}
        	    }
        	    
        	    ImgMat img = new ImgMat(db);
        	    img.GetRegionofInterest();
        	    img.ScaleDown(15, 24);
        	    
        	    double trn[] = img.TolinearArray();
        	    
        	    
            	
            	
            	if(train%500==0){
            		double err = nn.Error(trn, teacher);
            		System.out.println("Train: " + train + " Error: "+err);
            		if(err<0.0005){
            			ret = false;
            		}
            	}else{
            		nn.Train(trn, teacher);
            	}
            	//System.out.println(train);
            	teacher[a] = 0;
            	train--;
            }
            
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
          fos = new FileOutputStream("networkk.ser");
          out = new ObjectOutputStream(fos);
          out.writeObject(nn);

          out.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
	}

}
