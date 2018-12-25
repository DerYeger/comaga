package eu.yeger.comaga.model;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Field 
{

   public static final String PROPERTY_occupied = "occupied";

   private boolean occupied;

   public boolean getOccupied()
   {
      return occupied;
   }

   public Field setOccupied(boolean value)
   {
      if (value != this.occupied)
      {
         boolean oldValue = this.occupied;
         this.occupied = value;
         firePropertyChange("occupied", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_color = "color";

   private String color;

   public String getColor()
   {
      return color;
   }

   public Field setColor(String value)
   {
      if (value == null ? this.color != null : ! value.equals(this.color))
      {
         String oldValue = this.color;
         this.color = value;
         firePropertyChange("color", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_grid = "grid";

   private Grid grid = null;

   public Grid getGrid()
   {
      return this.grid;
   }

   public Field setGrid(Grid value)
   {
      if (this.grid != value)
      {
         Grid oldValue = this.grid;
         if (this.grid != null)
         {
            this.grid = null;
            oldValue.withoutFields(this);
         }
         this.grid = value;
         if (value != null)
         {
            value.withFields(this);
         }
         firePropertyChange("grid", oldValue, value);
      }
      return this;
   }



public static final java.util.ArrayList<Field> EMPTY_neighbors = new java.util.ArrayList<Field>()
   { @Override public boolean add(Field value){ throw new UnsupportedOperationException("No direct add! Use xy.withNeighbors(obj)"); }};


public static final String PROPERTY_neighbors = "neighbors";

private java.util.ArrayList<Field> neighbors = null;

public java.util.ArrayList<Field> getNeighbors()
   {
      if (this.neighbors == null)
      {
         return EMPTY_neighbors;
      }

      return this.neighbors;
   }

public Field withNeighbors(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withNeighbors(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.neighbors == null)
            {
               this.neighbors = new java.util.ArrayList<Field>();
            }
            if ( ! this.neighbors.contains(item))
            {
               this.neighbors.add((Field)item);
               ((Field)item).withNeighbors(this);
               firePropertyChange("neighbors", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }


public Field withoutNeighbors(Object... value)
   {
      if (this.neighbors == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutNeighbors(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.neighbors.contains(item))
            {
               this.neighbors.remove((Field)item);
               ((Field)item).withoutNeighbors(this);
               firePropertyChange("neighbors", item, null);
            }
         }
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

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getColor());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setGrid(null);

      this.withoutNeighbors(this.getNeighbors().clone());


      this.withoutNeighbors(this.getNeighbors().clone());


   }


}