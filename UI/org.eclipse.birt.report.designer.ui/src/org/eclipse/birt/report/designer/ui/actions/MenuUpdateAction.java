/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.designer.ui.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.birt.report.designer.internal.ui.util.Policy;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Action used to update dynamic items of menu.
 */

public abstract class MenuUpdateAction extends SelectionAction
{

	private MenuManager menu;

	private List actionItems;

	/**
	 * Dynamic items should subclass this action. Menu action(subclass of
	 * MenuUpdateAction) will update this item.
	 */
	public static abstract class DynamicItemAction extends Action
	{

		private ISelection selection;

		/**
		 * Sets the current selection.
		 * 
		 * @param selection
		 *            The new selection.
		 */
		public void setSelection( ISelection selection )
		{
			this.selection = selection;
		}

		/**
		 * Gets the current selection.
		 * 
		 * @return The current selection.
		 */
		protected ISelection getSelection( )
		{
			return selection;
		}
	}

	/**
	 * @param part
	 */
	public MenuUpdateAction( IWorkbenchPart part )
	{
		super( part );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled( )
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run( )
	{
		if ( Policy.TRACING_ACTIONS )
		{
			System.out.println( "Action [" + getClass() + "] >> Run ..." ); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if ( menu != null )
		{
			actionItems = getItems( );
			menu.removeAll( );
			for ( Iterator i = actionItems.iterator( ); i.hasNext( ); )
			{
				DynamicItemAction action = (DynamicItemAction) i.next( );
				if ( action != null )
				{
					action.setSelection( getSelection( ) );
					menu.add( action );
				}
				else
				{
					menu.add( new Separator( ) );
				}
			}
			if ( menu.getItems( ).length == 0 )
			{
				menu.add( NoneAction.getInstance( ) );
			}
			menu.update( true );
		}
	}

	/**
	 * Updates then current menu.
	 * 
	 * @param menu
	 *            the current menu
	 */
	public void updateMenu( MenuManager menu )
	{
		this.menu = menu;
		run( );
	}

	/**
	 * Gets dynamic items of current menu to generate sub-menu. If the element
	 * returns null, menu will generate separator.
	 * 
	 * @return items
	 */
	abstract protected List getItems( );
}