package ntou.jt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class wordSeg 
{
	public FileWriter fw;
	void readingSentenceAndCheck()
	{
		//�g�ɹw��
		try {
			fw = new FileWriter("result.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//��l�Ʀr��
		ArrayList<String> lex = null;
		try {
			lex = initialLex();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		//��l�ƿ�J�r��
		ArrayList<String> input = null;
		try {
			input = initialInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�}�l���R�r��
		for(int k = 0;k < input.size();k++)
		{
			String userInput = input.get(k);
			System.out.println("�}�l���R�r��y"+userInput+"�z");
					
			//i~k���Rtree���c�A�}�l�إ�Word lattice
					
			//totalLattice�O�ĤG�h(�̥~�h)�A�Ψ��x�s���R�L�᪺latticeTmp�A�e�ᶶ�ǧY����J�r�ꪺ�C�Ӧr����
			ArrayList<ArrayList<String>> totalLattice = new ArrayList<ArrayList<String>>();
					
			//���J���r�C�Ӧr���}�Ӥ��R
			for(int i = 0;i < userInput.length();i++)
			{
				//latticeTmp�O�Ĥ@�h���A���R�H�o�Ӧr���}�Y�����Ǧr���ŦX(i������R�A��i = lenth)
				ArrayList<String> latticeTmp = new ArrayList<String>();
						
				//�}�l���R
				for(int j = i+1;j < userInput.length()+1;j++)
				{
					//userInput.substring(i, j) �� ��i~��j�Ӧr
					String word = userInput.substring(i, j);
					//��i�r����R�A�p�G�T�w�b�r�夺�N���arraylist��
					if(checkLexicon(lex,word))
					{
						latticeTmp.add(word);
					}
				}
						
				//���i�Ӧr�}�Y�����R����`Lattice��
				totalLattice.add(latticeTmp);
			}
			//prtTotalLattice(totalLattice);
			checkingLoop("",totalLattice.get(0),0,totalLattice);
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	ArrayList<String> initialLex() throws IOException
	{
		File f = new File("resource/wordSeg_testDictionary_1061.txt"); 

		InputStreamReader read = new InputStreamReader (new FileInputStream(f),"UTF-8"); 

		BufferedReader reader=new BufferedReader(read); 

		String line; 
		ArrayList<String> tmp = new ArrayList<String>();
		
		while ((line = reader.readLine()) != null) 
		{ 
			tmp.add(line);
			//System.out.println(line);
		}
		return tmp;
	}
	
	ArrayList<String> initialInput() throws IOException
	{
		File f = new File("resource/wordSeg_testInput_1061.txt"); 

		InputStreamReader read = new InputStreamReader (new FileInputStream(f),"UTF-8"); 

		BufferedReader reader=new BufferedReader(read); 

		String line; 
		ArrayList<String> tmp = new ArrayList<String>();
		
		while ((line = reader.readLine()) != null) 
		{ 
			tmp.add(line);
			//System.out.println(line);
		}
		return tmp;
	}
	
	
	void startCheckWord()
	{
		//��l�Ʀr��
		ArrayList<String> lex = null;
		try {
			lex = initialLex();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�ϥΪ̿�J���R�r��
		System.out.println("�п�J�����R�r��G");
		String userInput;
		Scanner input = new Scanner(System.in);
		userInput = input.nextLine();
		System.out.println("�}�l���R�r��y"+userInput+"�z");
		
		//i~k���Rtree���c�A�}�l�إ�Word lattice
		
		//totalLattice�O�ĤG�h(�̥~�h)�A�Ψ��x�s���R�L�᪺latticeTmp�A�e�ᶶ�ǧY����J�r�ꪺ�C�Ӧr����
		ArrayList<ArrayList<String>> totalLattice = new ArrayList<ArrayList<String>>();
		
		//���J���r�C�Ӧr���}�Ӥ��R
		for(int i = 0;i < userInput.length();i++)
		{
			//latticeTmp�O�Ĥ@�h���A���R�H�o�Ӧr���}�Y�����Ǧr���ŦX(i������R�A��i = lenth)
			ArrayList<String> latticeTmp = new ArrayList<String>();
			
			//�}�l���R
			for(int j = i+1;j < userInput.length()+1;j++)
			{
				//userInput.substring(i, j) �� ��i~��j�Ӧr
				String word = userInput.substring(i, j);
				//��i�r����R�A�p�G�T�w�b�r�夺�N���arraylist��
				if(checkLexicon(lex,word))
				{
					latticeTmp.add(word);
				}
			}
			
			//���i�Ӧr�}�Y�����R����`Lattice��
			totalLattice.add(latticeTmp);
		}
		//prtTotalLattice(totalLattice);
		checkingLoop("",totalLattice.get(0),0,totalLattice);
	}
	
	boolean checkLexicon(ArrayList<String> lex,String word)
	{
		for(int i = 0;i < lex.size();i++)
		{
			//�p�G�T�{�r�夺���o�Ӧr��
			if(lex.get(i).equals(word))
			{
				return true;
			}
		}
		return false;
	}
	
	void prtTotalLattice(ArrayList<ArrayList<String>> totalLattice)//�o�u�O�ΨӴ��եΪ�~���էڪ�Lattice�O���O���T��
	{
		for(int i = 0;i < totalLattice.size();i++)
		{
			ArrayList<String> tmp = totalLattice.get(i);
			System.out.println("("+tmp.get(0)+"�}�Y)");
			for(int j = 0;j < tmp.size();j++)
			{
				System.out.println(tmp.get(j));
			}
		}
	}
	
	void checkingLoop(String nowSentence,ArrayList<String> nextLattice,int top,ArrayList<ArrayList<String>> totalLattice)
	{
		//System.out.println("�ثe���R�G"+nowSentence);
		
		for(int i = 0;i < nextLattice.size();i++)
		{
			String tmp = nextLattice.get(i);
			
			//�����P�w
			if(top+tmp.length() >= totalLattice.size())
			{
				
				try {
					fw.write(nowSentence+tmp+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(nowSentence+tmp);
				return;
			}
			
			checkingLoop(nowSentence+tmp+" ",totalLattice.get(top+tmp.length()),top+tmp.length(),totalLattice);
		}
		
	}
	
	public static void main(String args[])
	{
		wordSeg wordSeg = new wordSeg();
		//�o�O�ۤv��J��function
		//wordSeg.startCheckWord();
		//�o�OŪ�ɤ��R��function
		wordSeg.readingSentenceAndCheck();
	}
}
