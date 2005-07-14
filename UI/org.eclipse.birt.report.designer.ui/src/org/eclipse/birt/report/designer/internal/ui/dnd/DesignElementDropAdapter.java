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

package org.eclipse.birt.report.designer.internal.ui.dnd;

import org.eclipse.birt.report.designer.internal.ui.util.Policy;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

/**
 * Supports dropping elements in outline view.
 */

public abstract class DesignElementDropAdapter extends ViewerDropAdapter
{

	/**
	 * Constructor
	 * 
	 * @param viewer
	 */
	public DesignElementDropAdapter( TreeViewer viewer )
	{
		super( viewer );
	}

	/**
	 * @see ViewerDropAdapter#dragOver(DropTargetEvent)
	 * 
	 */
	public void dragOver( DropTargetEvent event )
	{
		super.dragOver( event );
		if ( event.detail == DND.DROP_NONE )
			return;
		if ( !validateTarget( getCurrentTarget( ) )
				|| !validateTarget( getCurrentTarget( ),
						TemplateTransfer.getInstance( ).getTemplate( ) ) )
		{
			event.detail = DND.DROP_NONE;
			if ( Policy.TRACING_DND_DRAG )
			{
				System.out.println( "DND >> Drag over " + event.getSource( ) ); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @see ViewerDropAdapter#performDrop(Object)
	 */
	public boolean performDrop( Object data )
	{
		if ( getCurrentOperation( ) == DND.DROP_MOVE )
		{
			if ( Policy.TRACING_DND_DRAG )
			{
				System.out.println( "DND >> Dropped. Operation: Copy, Target: " //$NON-NLS-1$
						+ getCurrentTarget( ) );
			}
			return moveData( data, getCurrentTarget( ) );
		}
		else if ( getCurrentOperation( ) == DND.DROP_COPY )
		{
			if ( Policy.TRACING_DND_DRAG )
			{
				System.out.println( "DND >> Dropped. Operation: Move, Target: " //$NON-NLS-1$
						+ getCurrentTarget( ) );
			}
			return copyData( data, getCurrentTarget( ) );
		}
		return false;
	}

	/**
	 * @see ViewerDropAdapter#validateDrop(Object, int, TransferData)
	 */
	public boolean validateDrop( Object target, int op, TransferData type )
	{
		return TemplateTransfer.getInstance( ).isSupportedType( type );
	}

	/**
	 * Validates target elements can be dropped
	 * 
	 * @param target
	 *            target elements
	 * @return if target elements can be dropped
	 */
	protected abstract boolean validateTarget( Object target );

	/**
	 * Validates target elements can contain transfer data data
	 * 
	 * @param target
	 *            target elements
	 * @param transfer
	 *            transfer data
	 * @return if target elements can be dropped
	 */
	protected abstract boolean validateTarget( Object target, Object transfer );

	/**
	 * Moves elements
	 * 
	 * @param transfer
	 *            transfer elements.
	 * @param target
	 *            target elements
	 * @return if succeeding in moving data
	 */
	protected abstract boolean moveData( Object transfer, Object target );

	/**
	 * Copys elements
	 * 
	 * @param transfer
	 *            transfer elements.
	 * @param target
	 *            target elements
	 * @return if succeeding in copying data
	 */
	protected abstract boolean copyData( Object transfer, Object target );
}