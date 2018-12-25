package eu.yeger.comaga.model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Game 
{

   public static final String PROPERTY_score = "score";

   private int score;

   public int getScore()
   {
      return score;
   }

   public Game setScore(int value)
   {
      if (value != this.score)
      {
         int oldValue = this.score;
         this.score = value;
         firePropertyChange("score", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_grid = "grid";

   private Grid grid = null;

   public Grid getGrid()
   {
      return this.grid;
   }

   public Game setGrid(Grid value)
   {
      if (this.grid != value)
      {
         Grid oldValue = this.grid;
         if (this.grid != null)
         {
            this.grid = null;
            oldValue.setGame(null);
         }
         this.grid = value;
         if (value != null)
         {
            value.setGame(this);
         }
         firePropertyChange("grid", oldValue, value);
      }
      return this;
   }



   protected PropertyChangeSupport listeners = null;

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null)
      {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }



   public void removeYou()
   {
      this.setGrid(null);

   }


}