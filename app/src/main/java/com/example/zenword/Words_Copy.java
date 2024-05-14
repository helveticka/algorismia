package com.example.zenword;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;

import DataStructures.*;


public class Words_Copy implements Serializable
{
    private String paraulaTriada;
    private int wordLength, guessingRows, numParaulesValides, numParaulesEncertades;

    public final static int minCombinationLength = 3;
    public final static int minWordsLength = 4;
    public final static int maxWordsLength = 7;
    public final static int maxGuessingRows = 5;

    private UnsortedArrayMapping<Integer, UnsortedLinkedListSet<String>> paraulesValides;
    private UnsortedLinkedListMapping<Integer, UnsortedLinkedListSet<String>> longituds;
    private BSTMapping<String, Boolean> solucions;
    private UnsortedArrayMapping<String, Integer> paraulesOcultes;
    private UnsortedArrayMapping<String, Integer> paraulesOcultesTrobades;


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

        paraulesOcultesTrobades = new UnsortedArrayMapping<>(maxGuessingRows);
        solucions = new BSTMapping<>();
        numParaulesEncertades = 0;
    }


    public String getParaulaTriada() {return paraulaTriada;}


    public int getWordLength() {return wordLength;}

    public int getGuessingRows() {return guessingRows;}


    public int getNumParaulesValides() {return numParaulesValides;}
    public int getNumParaulesEncertades() {return numParaulesEncertades;}

    public UnsortedArrayMapping<String, Integer> getParaulesOcultes() {return paraulesOcultes;}

    public UnsortedArrayMapping<String, Integer> getParaulesOcultesTrobades() {return paraulesOcultesTrobades;}


    private void createLongituds(MainActivity mainActivity)
    {
        try
        {
            //InputStream is = mainActivity.getResources().openRawResource(R.raw.paraules);
            InputStream is = mainActivity.getResources().openRawResource(R.raw.paraules2);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            longituds = new UnsortedLinkedListMapping<>();

            String line = br.readLine();
            while (line != null)
            {
                int len = line.length()/2;
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
        UnsortedArrayMapping<Integer, BSTSet<String>> res = new UnsortedArrayMapping<>(maxGuessingRows);
        int count = 0, i = minCombinationLength;

        for (; i<=wordLength; i++)
        {
            String s = randomWord(paraulesValides.get(i));
            if (s != null)
            {
                /*aux.add(s);
                count++;
                numParaulesValides[i-1]--;*/

                BSTSet<String> list = res.get(s.length()/2);
                if (list == null)
                {
                    list = new BSTSet<>();
                    list.add(s);
                    res.put(s.length()/2, list);
                }
                else
                {
                    list.add(s);
                }

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
                    /*if (aux.add(s))
                    {
                        count++;
                        numParaulesValides[i-1]--;
                    }*/

                    BSTSet<String> list = res.get(s.length()/2);
                    if (list.add(s))
                    {
                        count++;
                        numParaulesValidesArr[i-1]--;
                    }
                }
            }
            else i++;
        }

        paraulesOcultes = new UnsortedArrayMapping<>(count);
        //Iterator it = aux.iterator();
        Iterator itRes = res.iterator();
        i = 0;

        /*while (it.hasNext())
        {
            paraulesOcultes.put((String) it.next(), i++);
        }*/

        while (itRes.hasNext())
        {
            UnsortedArrayMapping.Pair pair = (UnsortedArrayMapping.Pair) itRes.next();

            @SuppressWarnings("unchecked")
            Iterator itList = ((BSTSet<String>) pair.getValue()).iterator();
            while (itList.hasNext())
            {
                paraulesOcultes.put((String) itList.next(), i++);
            }
        }

        return count;
    }


    public boolean esParaulaValida(String s)
    {
        boolean b = true;

        if (b)
        {
            if (solucions.get(s) == null)
            {
                numParaulesEncertades++;
                solucions.add(s, false);
                return true;
            }
            else
            {
                solucions.add(s, true);
            }
        }
        return false;
    }


    public int esParauaOculta(String s)
    {

        //i guardar a trobades
        return -1;  //-1 si no hi és, sa posició de sa fila si hi fos
    }


    public SpannableStringBuilder getParaulesTorbades(boolean repetides)
    {
        SpannableStringBuilder s = new SpannableStringBuilder();

        Iterator it = solucions.iterator();
        while (it.hasNext())
        {
            BSTMapping.Pair pair = (BSTMapping.Pair) it.next();
            String str = (String) pair.getKey();
            int start = s.length();
            s.append(str).append(", ");

            if (repetides && ((Boolean) pair.getValue()))
            {
                s.setSpan(new ForegroundColorSpan(Color.RED), start, (start+str.length()), 0);
            }
        }

        if (s.length() > 0) s.replace(s.length()-2, s.length()-1, "");
        return s;
    }

}
