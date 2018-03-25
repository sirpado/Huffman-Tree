package assign1;

public class Huffman {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HufmannEncoderDecoder h = new HufmannEncoderDecoder();
		//hello@
		String [] arr = {"C:\\Users\\sirpa\\Documents\\eclipse-projects\\Huffman\\src\\assign1\\byte.txt","compressed.txt"};
		h.Compress(arr, arr);

	}

}
