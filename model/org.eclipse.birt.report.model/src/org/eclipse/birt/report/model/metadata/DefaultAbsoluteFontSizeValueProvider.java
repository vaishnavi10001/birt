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

package org.eclipse.birt.report.model.metadata;

import org.eclipse.birt.report.model.api.IAbsoluteFontSizeValueProvider;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.eclipse.birt.report.model.api.metadata.DimensionValue;

/**
 * Represents the default implementation for
 * <code>IAbsoluteFontSizeValueProvider</code>. This provider defines
 * <ul>
 * <li>xx-small = 6px
 * <li>x-small = 8px
 * <li>small = 10px
 * <li>medium = 12px
 * <li>large = 14px
 * <li>x-large = 16px
 * <li>xx-large = 18px
 * </ul>
 **/

public class DefaultAbsoluteFontSizeValueProvider
		implements
			IAbsoluteFontSizeValueProvider
{

	private static DefaultAbsoluteFontSizeValueProvider instance = new DefaultAbsoluteFontSizeValueProvider( );

	private static final DimensionValue xxSmall = new DimensionValue( 6,
			DesignChoiceConstants.UNITS_PX );
	private static final DimensionValue xSmall = new DimensionValue( 8,
			DesignChoiceConstants.UNITS_PX );
	private static final DimensionValue small = new DimensionValue( 10,
			DesignChoiceConstants.UNITS_PX );
	private static final DimensionValue medium = new DimensionValue( 12,
			DesignChoiceConstants.UNITS_PX );
	private static final DimensionValue large = new DimensionValue( 14,
			DesignChoiceConstants.UNITS_PX );
	private static final DimensionValue xLarge = new DimensionValue( 16,
			DesignChoiceConstants.UNITS_PX );
	private static final DimensionValue xxLarge = new DimensionValue( 18,
			DesignChoiceConstants.UNITS_PX );

	/**
	 * Returns the instance.
	 * 
	 * @return the instance
	 */

	public static DefaultAbsoluteFontSizeValueProvider getInstance( )
	{
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.api.IAbsoluteFontSizeValueProvider#getValueOf(java.lang.String)
	 */
	
	public DimensionValue getValueOf( String fontSizeConstant )
	{
		if ( DesignChoiceConstants.FONT_SIZE_XX_SMALL
				.equalsIgnoreCase( fontSizeConstant ) )
		{
			return xxSmall;
		}
		else if ( DesignChoiceConstants.FONT_SIZE_X_SMALL
				.equalsIgnoreCase( fontSizeConstant ) )
		{
			return xSmall;
		}
		else if ( DesignChoiceConstants.FONT_SIZE_SMALL
				.equalsIgnoreCase( fontSizeConstant ) )
		{
			return small;
		}
		else if ( DesignChoiceConstants.FONT_SIZE_MEDIUM
				.equalsIgnoreCase( fontSizeConstant ) )
		{
			return medium;
		}
		else if ( DesignChoiceConstants.FONT_SIZE_LARGE
				.equalsIgnoreCase( fontSizeConstant ) )
		{
			return large;
		}
		else if ( DesignChoiceConstants.FONT_SIZE_X_LARGE
				.equalsIgnoreCase( fontSizeConstant ) )
		{
			return xLarge;
		}
		else if ( DesignChoiceConstants.FONT_SIZE_XX_LARGE
				.equalsIgnoreCase( fontSizeConstant ) )
		{
			return xxLarge;
		}

		return null;
	}

}