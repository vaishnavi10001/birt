/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.designer.internal.ui.editors.schematic.actions;

import org.eclipse.birt.report.designer.internal.ui.editors.schematic.editparts.TableEditPart;
import org.eclipse.birt.report.designer.internal.ui.util.Policy;
import org.eclipse.birt.report.designer.nls.Messages;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * Action delete a row or multi rows of a table or a grid.
 *  
 */
public class DeleteRowAction extends ContextSelectionAction
{

	/** action text */
	private static final String ACTION_MSG_DELETE = Messages.getString( "DeleteRowAction.actionMsg.delete" ); //$NON-NLS-1$

	/** action ID for registry */
	public static final String ID = "org.eclipse.birt.report.designer.internal.ui.editors.schematic.actions.DeleteRowAction"; //$NON-NLS-1$

	/**
	 * Constructs a new instance of this action.
	 * 
	 * @param part
	 *            The current work bench part
	 */
	public DeleteRowAction( IWorkbenchPart part )
	{
		super( part );
		setId( ID );
		setText( ACTION_MSG_DELETE );
		ISharedImages shareImages = PlatformUI.getWorkbench( )
				.getSharedImages( );
		setImageDescriptor( shareImages.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE ) );
		setDisabledImageDescriptor( shareImages.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE_DISABLED ) );
		setAccelerator( SWT.DEL );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled( )
	{
		return !getRowHandles( ).isEmpty( );
	}

	/**
	 * Runs this action.
	 *  
	 */
	public void run( )
	{
		if ( Policy.TRACING_ACTIONS )
		{
			System.out.println( "Delete row action >> Run ..." ); //$NON-NLS-1$
		}
		TableEditPart part = getTableEditPart( );
		if ( part != null )
		{
			EditPartViewer viewer = part.getViewer( );
			part.deleteRow( getRowNumbers( ) );
			viewer.select( part );
		}
	}

}