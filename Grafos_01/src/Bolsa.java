import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bolsa <Item> implements Iterable<Item> 
{
    
    private Node<Item> primeiro; // Início da lista
    private int n; // Contador de elementos

    private static class Node<Item> 
    {
        private Item item;
        private Node<Item> proximo;
    }

    public Bolsa() 
    {
        primeiro = null;
        n = 0;
    }

    public boolean estaVazia() 
    {
        return primeiro == null;
    }

    public int tamanho() {
        return n;
    }

    // Adiciona no início da lista
    public void add (Item item)
    {
        Node <Item> antigoPrimeiro = primeiro;
        primeiro = new Node <Item>();
        primeiro.item = item;
        primeiro.proximo = antigoPrimeiro;
        n++;
    }

     // Implementação da interface Iterable
    public Iterator<Item> iterator() 
    {
        return new ListIterator<Item>(primeiro);
    }   

    private class ListIterator <Item> implements Iterator <Item> 
    {
        private Node<Item> atual;

        // Começa no primeiro nó da bolsa
        public ListIterator(Node<Item> primeiro)
        {
            atual = primeiro;
        }

        // Verifica se ainda há nós para percorrer
        public boolean hasNext()
        {
            return atual != null;
        }

        // Entrega o item atual e pula para o próximo nó
        public Item next()
        {
            if(hasNext() == true)
            {
                Item item = atual.item;
                atual = atual.proximo;
                return item;
            }
            else
            {
                // Se hasNext() for falso, não há o que retornar, então lançamos o erro
                throw new NoSuchElementException("A lista terminou, não há mais vizinhos.");
            }
        }


    } 

}

