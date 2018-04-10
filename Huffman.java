package assign1;

public class Huffman {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HufmannEncoderDecoder h = new HufmannEncoderDecoder();
		
		String [] arr = {"C:\\Users\\sirpa\\Documents\\Sapir\\Data Compression\\Ass1ExampeInputs\\london_in_polish_source.txt",
				"C:\\Users\\sirpa\\Documents\\Sapir\\Data Compression\\Ass1ExampeInputs\\OnTheOrigin.txt",
				"C:\\Users\\sirpa\\Documents\\Sapir\\Data Compression\\Ass1ExampeInputs\\OnTheOrigin_C2.txt",
				"C:\\Users\\sirpa\\Documents\\Sapir\\Data Compression\\Ass1ExampeInputs\\YouKnowThisSound"};
		String [] arr1 = {"C:\\Users\\sirpa\\Documents\\eclipse-projects\\Huffman\\src\\assign1\\1.txt",
				"C:\\Users\\sirpa\\Documents\\eclipse-projects\\Huffman\\src\\assign1\\2.txt",
				"C:\\Users\\sirpa\\Documents\\eclipse-projects\\Huffman\\src\\assign1\\3.txt",
				"C:\\Users\\sirpa\\Documents\\eclipse-projects\\Huffman\\src\\assign1\\4.txt"};
		h.Compress(arr, arr1);

	}

}
