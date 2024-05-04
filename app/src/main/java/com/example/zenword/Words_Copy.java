package com.example.zenword;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;

import UnsortedElements.UnsortedArrayMapping;
import UnsortedElements.UnsortedLinkedListMapping;
import UnsortedElements.UnsortedLinkedListSet;


public class Words_Copy
{
    private String paraulaTriada;
    private int wordLength, guessingRows, numParaulesValides;

    public final static int minCombinationLength = 3;
    public final static int minWordsLength = 4;
    public final static int maxWordsLength = 7;
    public final static int maxGuessingRows = 5;

    private UnsortedArrayMapping<Integer, UnsortedLinkedListSet<String>> paraulesValides;
    private UnsortedLinkedListMapping<Integer, UnsortedLinkedListSet<String>> longituds;
    private UnsortedLinkedListMapping<Integer, UnsortedLinkedListSet<String>> solucions;
    private UnsortedArrayMapping<String, Integer> paraulesOcultes;
    private UnsortedLinkedListSet<String> trobades;


    public Words_Copy(MainActivity mainActivity)
    {
        createLongituds(mainActivity);
    }


    public void novaParaula()
    {
        wordLength = new Random().nextInt(maxWordsLength-minWordsLength+1) + minWordsLength;
        paraulaTriada = randomWord(longituds.get(wordLength));

        int[] numParaulesValidesArr = new int[maxWordsLength];
        numParaulesValides = createParaulesValides(numParaulesValidesArr);

        guessingRows = createParaulesOcultes(numParaulesValidesArr);

        trobades = new UnsortedLinkedListSet<>();
    }


    public String getParaulaTriada() {return paraulaTriada;}


    public int getWordLength() {return wordLength;}


    public int getGuessingRows() {return guessingRows;}


    public int getNumParaulesValides() {return numParaulesValides;}

    public UnsortedArrayMapping<String, Integer> getParaulesOcultes() {return paraulesOcultes;}


    private void createLongituds(MainActivity mainActivity)
    {
        try
        {
            InputStream is = mainActivity.getResources().openRawResource(R.raw.paraules);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            longituds = new UnsortedLinkedListMapping<>();

            String line = br.readLine();
            while (line != null)
            {
                int len = (line.length()-1)/2;
                if ((len >= minCombinationLength) && (len <= maxWordsLength))
                {
                    UnsortedLinkedListSet<String> list = longituds.get(len);
                    if (list == null)
                    {
                        list = new UnsortedLinkedListSet<>();
                        list.add(line);
                        longituds.put(len, list);
                    }
                    else
                    {
                        list.add(line);
                    }
                }

                line = br.readLine();
            }
        }
        catch (Resources.NotFoundException | IOException e) {throw new RuntimeException(e);}

    }


    private String randomWord(UnsortedLinkedListSet<String> list)
    {
        if (list == null) return null;

        Random ran = new Random();
        Iterator it = list.iterator();
        String word = (String) it.next();

        for (int i=2; it.hasNext(); i++)
        {
            if ((ran.nextInt(i) % i) == 0)
            {
                word = (String) it.next();
            }
            else
            {
                it.next();
            }
        }

        return word;
    }


    private int createParaulesValides(int[] numParaulesValidesArr)
    {
        paraulesValides = new UnsortedArrayMapping<>(maxWordsLength);
        int count = 0;

        for (int i=minCombinationLength; i<=wordLength; i++)
        {
            //System.out.println(i + " lletres");
            UnsortedLinkedListSet<String> list = new UnsortedLinkedListSet<>();
            numParaulesValidesArr[i-1] = 0;

            UnsortedLinkedListSet<String> longitudsList = longituds.get(i);
            if (longitudsList != null)
            {
                Iterator it = longitudsList.iterator();
                while (it.hasNext())
                {
                    String word = (String) it.next();
                    boolean b = esParaulaSolucio(paraulaTriada.split(";")[1], word.split(";")[1]);
                    if (b)
                    {
                        //System.out.println("\t" + word);
                        list.add(word);
                        numParaulesValidesArr[i-1]++;
                    }
                }
            }

            if (!list.isEmpty()) paraulesValides.put(i, list);
            count += numParaulesValidesArr[i-1];
        }

        return count;
    }


    private boolean esParaulaSolucio(String paraula1, String paraula2)
    {
        UnsortedArrayMapping<Character, Integer> lletresDisponibles = new UnsortedArrayMapping<>(paraula1.length());

        //començam ficant la primera paraula a un hashmap lletra per lletra
        //on la clau es la lletra i el valor el numero de aparicions
        for (char c : paraula1.toCharArray())
        {
            //si la lletra no está al hashmap es fica amb valor 1
            if (lletresDisponibles.get(c) == null)
            {
                lletresDisponibles.put(c, 1);
            }
            //si la lletra ja era al hashmap es fica amb valor anterior+1
            else
            {
                int number = lletresDisponibles.get(c);
                lletresDisponibles.put(c, number+1);
            }
        }


        int i = 0;
        while (i < paraula2.length())
        {
            char c = paraula2.charAt(i++);
            if (lletresDisponibles.get(c) == null) return false;
            else
            {
                int number = lletresDisponibles.get(c);
                if (number > 1)
                {
                    lletresDisponibles.put(c, number-1);
                }
                else
                {
                    lletresDisponibles.remove(c);
                }
            }
        }

        return true;
    }


    private int createParaulesOcultes(int[] numParaulesValidesArr)
    {
        UnsortedLinkedListSet<String> aux = new UnsortedLinkedListSet<>();
        int count = 0, i = minCombinationLength;

        for (; i<=wordLength; i++)
        {
            String s = randomWord(paraulesValides.get(i));
            if (s != null)
            {
                aux.add(s);
                count++;
                numParaulesValidesArr[i-1]--;
            }
        }

        i = minCombinationLength;
        while ((count < maxGuessingRows) && (i <= wordLength))
        {
            if (numParaulesValidesArr[i-1] > 0)
            {
                String s = randomWord(paraulesValides.get(i));
                if (s != null)
                {
                    if (aux.add(s))
                    {
                        count++;
                        numParaulesValidesArr[i-1]--;
                    }
                }
            }
            else i++;
        }

        paraulesOcultes = new UnsortedArrayMapping<>(count);
        aux.mergeSort();
        Iterator it = aux.iterator();
        i = 0;

        while (it.hasNext())
        {
            paraulesOcultes.put((String) it.next(), i++);
        }

        return count;
    }


    public boolean esParaulaValida(String s)
    {


        trobades.add(s);
        return true;
    }


    public int esParauaOculta(String s)
    {

        return -1;
    }


    public String getParaulesTorbades()
    {
        StringBuilder s = new StringBuilder();
        trobades.mergeSort();

        Iterator it = trobades.iterator();
        while (it.hasNext())
        {
            s.append(it.next()).append(", ");
        }

        if (s.length() > 0) s.replace(s.length()-2, s.length()-1, "");
        return s.toString();
    }

}
