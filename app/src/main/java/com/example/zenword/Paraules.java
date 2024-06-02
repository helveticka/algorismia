package com.example.zenword;

import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import DataStructures.*;


public class Paraules implements Serializable
{
    private String paraulaTriada;
    private int wordLength, numFiles, numParaulesValides, numParaulesEncertades;

    public final static int minCombinationLength = 3;
    public final static int minWordLength = 4;
    public final static int maxWordLength = 7;
    public final static int maxFiles = 5;

    private UnsortedArrayMapping<Integer, HashMap<String, String>> paraulesValides;
    private UnsortedLinkedListMapping<Integer, HashMap<String, String>> longituds;

    private BSTMapping<String, Boolean> solucions;
    private UnsortedArrayMapping<String, Integer> paraulesOcultes;
    private UnsortedArrayMapping<String, Integer> paraulesTrobades;
    private UnsortedArrayMapping<String, Integer> ajudesDisponibles;
    private UnsortedArrayMapping<String, Integer> ajudesActuals;


    /**
     * Constructor de la classe Words.
     *
     * @param mainActivity L'activitat principal que proporciona els recursos necessaris.
     */
    public Paraules(MainActivity mainActivity)
    {
        createLongituds(mainActivity);
    }


    /**
     * Genera una nova paraula per a endevinar.
     */
    public void novaParaula()
    {
        wordLength = new Random().nextInt(maxWordLength - minWordLength + 1) + minWordLength;
        paraulaTriada = (String) MappingInterface.random(longituds.get(wordLength).entrySet().iterator()).getKey();

        int[] numParaulesValidesArr = new int[maxWordLength];
        numParaulesValides = createParaulesValides(numParaulesValidesArr);

        numFiles = createParaulesOcultes(numParaulesValidesArr);

        paraulesTrobades = new UnsortedArrayMapping<>(maxFiles);
        ajudesDisponibles = new UnsortedArrayMapping<>(maxFiles);
        ajudesActuals = new UnsortedArrayMapping<>(maxFiles);
        solucions = new BSTMapping<>();
        numParaulesEncertades = 0;
    }


    /**
     * @return La paraula triada.
     */
    public String getParaulaTriada()
    {
        return paraulaTriada;
    }

    /**
     * @return El numero de files per endevinar.
     */
    public int getNumFiles()
    {
        return numFiles;
    }

    /**
     * @return El numero de paraules vàlides.
     */
    public int getNumParaulesValides()
    {
        return numParaulesValides;
    }

    /**
     * @return El numero de paraules encertades.
     */
    public int getNumParaulesEncertades()
    {
        return numParaulesEncertades;
    }

    /**
     * @return UnsortedArrayMapping de paraules ocultes.
     */
    public UnsortedArrayMapping<String, Integer> getParaulesOcultes()
    {
        return paraulesOcultes;
    }

    /**
     * @return UnsortedArrayMapping de paraules trobades.
     */
    public UnsortedArrayMapping<String, Integer> getParaulesTrobades()
    {
        return paraulesTrobades;
    }

    /**
     * @return Un mapa d'ajudes.
     */
    public UnsortedArrayMapping<String, Integer> getAjudesDisponibles()
    {
        return ajudesDisponibles;
    }

    /**
     * @return Un mapa d'ajudes.
     */
    public UnsortedArrayMapping<String, Integer> getAjudesActuals()
    {
        return ajudesActuals;
    }

    /**
     * @return Paraula amb caràcters especials.
     */
    public String getParaulaFormatada(String s)
    {
        return paraulesValides.get(s.length()).get(s);
    }


    /**
     * Afegeix longituds a partir del fitxer paraules2.
     *
     * @param mainActivity emprat per accedir al fitxer paraules2.
     */
    private void createLongituds(MainActivity mainActivity)
    {
        try
        {
            InputStream is = mainActivity.getResources().openRawResource(R.raw.paraules2);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            longituds = new UnsortedLinkedListMapping<>();

            String line = br.readLine();
            while (line != null)
            {
                String[] aux = line.split(";");
                int len = aux[1].length();
                if ((len >= minCombinationLength) && (len <= maxWordLength))
                {
                    HashMap<String, String> list = longituds.get(len);
                    if (list == null)
                    {
                        list = new HashMap<>();
                        list.put(aux[1], aux[0]);
                        longituds.put(len, list);
                    }
                    else
                    {
                        list.put(aux[1], aux[0]);
                    }
                }

                line = br.readLine();
            }
        }
        catch (Resources.NotFoundException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param numParaulesValidesArr Un array que conté el nombre de paraules vàlides.
     * @return El nombre de paraules vàlides.
     */
    private int createParaulesValides(int[] numParaulesValidesArr)
    {
        paraulesValides = new UnsortedArrayMapping<>(maxWordLength);
        int count = 0;

        for (int i = minCombinationLength; i <= wordLength; i++)
        {
            HashMap<String, String> list = new HashMap<>();
            numParaulesValidesArr[i-1] = 0;

            HashMap<String, String> longitudsList = longituds.get(i);
            if (longitudsList != null)
            {
                Iterator it = longitudsList.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry pair = (Map.Entry) it.next();
                    String word = (String) pair.getKey();
                    boolean b = esParaulaSolucio(paraulaTriada, word);
                    if (b)
                    {
                        list.put(word, (String) pair.getValue());
                        numParaulesValidesArr[i-1]++;
                    }
                }
            }

            if (!list.isEmpty()) paraulesValides.put(i, list);
            count += numParaulesValidesArr[i-1];
        }

        return count;
    }


    /**
     * Comprova si una paraula és una solució vàlida basada en una altra paraula.
     *
     * @param paraula1 La primera paraula que proporciona les lletres disponibles.
     * @param paraula2 La segona paraula que ha de ser verificada comprovant les lletres disponibles de la primera paraula.
     * @return true si la segona paraula es pot formar amb les lletres de la primera paraula, false en cas contrari.
     */
    private boolean esParaulaSolucio(String paraula1, String paraula2)
    {
        UnsortedArrayMapping<Character, Integer> lletresDisponibles = new UnsortedArrayMapping<>(paraula1.length());

        // Comença afegint la primera paraula a un hashmap lletra per lletra
        // on la clau és la lletra i el valor el número d'aparicions
        for (char c : paraula1.toCharArray())
        {
            // Si la lletra no està al hashmap es fica amb valor 1
            if (lletresDisponibles.get(c) == null)
            {
                lletresDisponibles.put(c, 1);
            }
            // Si la lletra ja era al hashmap es fica amb valor anterior+1
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


    /**
     * Crea les paraules ocultes a partir de l' array de paraules vàlides que no han estat triades.
     *
     * @param numParaulesValidesArr Array que conté el numero de paraules vàlides per a cada longitud.
     * @return El numero de paraules ocultes creades.
     */
    private int createParaulesOcultes(int[] numParaulesValidesArr)
    {
        UnsortedArrayMapping<Integer, BSTSet<String>> res = new UnsortedArrayMapping<>(maxFiles);
        int count = 0, i = minCombinationLength;

        for (; i <= wordLength; i++)
        {
            HashMap<String, String> aux = paraulesValides.get(i);
            if (aux == null) continue;

            String s = (String) MappingInterface.random(aux.entrySet().iterator()).getKey();

            BSTSet<String> list = res.get(s.length());
            if (list == null) {
                list = new BSTSet<>();
                list.add(s);
                res.put(s.length(), list);
            } else {
                list.add(s);
            }

            count++;
            numParaulesValidesArr[i-1]--;

            System.out.println(s);
        }

        i = minCombinationLength;
        while ((count < maxFiles) && (i <= wordLength))
        {
            if (numParaulesValidesArr[i-1] > 0)
            {
                HashMap<String, String> aux = paraulesValides.get(i);
                if (aux == null) continue;

                String s = (String) MappingInterface.random(aux.entrySet().iterator()).getKey();

                BSTSet<String> list = res.get(s.length());
                if (list.add(s))
                {
                    count++;
                    numParaulesValidesArr[i-1]--;
                    System.out.println(s);
                }
            } else i++;
        }

        paraulesOcultes = new UnsortedArrayMapping<>(count);
        Iterator itRes = res.iterator();
        i = 0;

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


    /**
     * Comprova si una paraula és vàlida.
     *
     * @param s La paraula a comprovar.
     * @return true si la paraula és vàlida, false en cas contrari.
     */
    public boolean esParaulaValida(String s)
    {
        if (s.length() < minCombinationLength) return false;

        String res = paraulesValides.get(s.length()).get(s);
        if (res != null)
        {
            if (solucions.get(res) == null)
            {
                numParaulesEncertades++;
                solucions.put(res, false);
                return true;
            }
            else
            {
                solucions.put(res, true);
            }
        }
        return false;
    }


    /**
     * Comprova si una paraula és oculta.
     *
     * @param s La paraula a comprovar.
     * @return L'índex de la paraula si és oculta, -1 en cas contrari.
     */
    public int esParaulaOculta(String s)
    {
        Integer res = paraulesOcultes.remove(s);
        if (res == null) return -1;

        paraulesTrobades.put(s, res);
        ajudesDisponibles.remove(s);
        ajudesActuals.remove(s);
        return res;
    }


    /**
     * Obté les paraules trobades en format HTML.
     *
     * @param repetides Si les paraules repetides han de ser marcades en vermell.
     * @return Les paraules trobades en format Spanned.
     */
    public Spanned getParaulesTorbades(boolean repetides)
    {
        StringBuilder s = new StringBuilder();
        if (repetides)
        {
            s = new StringBuilder("Has encertat " + numParaulesEncertades + " de " + numParaulesValides + " possibles: ");
        }

        Iterator it = solucions.iterator();
        while (it.hasNext())
        {
            BSTMapping.Pair pair = (BSTMapping.Pair) it.next();
            String str = (String) pair.getKey();

            if (repetides && ((Boolean) pair.getValue()))
            {
                s.append("<font color='red'>");
                s.append(str);
                s.append("</font>");
            }
            else s.append(str);

            s.append(", ");
        }

        if (s.length() == 0) return Html.fromHtml(s.toString());
        return Html.fromHtml(s.substring(0, s.length()-2));
    }

}
