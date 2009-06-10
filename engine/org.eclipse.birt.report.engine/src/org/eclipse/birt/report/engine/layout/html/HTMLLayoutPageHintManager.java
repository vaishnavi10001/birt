package org.eclipse.birt.report.engine.layout.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.presentation.IPageHint;
import org.eclipse.birt.report.engine.presentation.PageSection;
import org.eclipse.birt.report.engine.presentation.TableColumnHint;
import org.eclipse.birt.report.engine.presentation.UnresolvedRowHint;

public class HTMLLayoutPageHintManager
{
	protected HTMLLayoutContext context;
	
	protected HashMap layoutHint = new HashMap( );
	
	protected HashMap sizeBasedPageBreakHints = new HashMap( );

	protected ArrayList pageHints = new ArrayList( );
	
	public HTMLLayoutPageHintManager( HTMLLayoutContext context )
	{
		this.context = context;
	}
	
	public void setPageHint( List hints )
	{
		pageHints.addAll( hints );
	}

	public ArrayList getPageHint( )
	{
		ArrayList hints = new ArrayList( );
		hints.addAll( pageHints );
		return hints;
	}
	
	public void reset( )
	{
		layoutHint = new HashMap( );
		context.setFinish( false );
		context.setAllowPageBreak( true );
		context.setMasterPage( null );
	}

	public void addLayoutHint( IContent content, boolean finished )
	{
		layoutHint.put( content, new Boolean( finished ) );
	}
	
	public void removeLayoutHint(IContent content)
	{
		layoutHint.remove( content );
	}

	public boolean getLayoutHint( IContent content )
	{
		Object finished = layoutHint.get( content );
		if ( finished != null && finished instanceof Boolean )
		{
			return ( (Boolean) finished ).booleanValue( );
		}
		return true;
	}

	public void removeLayoutHint( )
	{
		layoutHint.clear( );
	}
	
	protected HashMap<String, UnresolvedRowHint> currentPageUnresolvedRowHints = new HashMap<String, UnresolvedRowHint>();	
	
	public HashMap<String, UnresolvedRowHint> getCurrentPageUnresolvedRowHints( )
	{
		return currentPageUnresolvedRowHints;
	}

	protected HashMap<String, UnresolvedRowHint> docRangeUnresolvedRowHints = new HashMap<String, UnresolvedRowHint>();
	
	public void generatePageRowHints(Collection<String> keys )
	{
		pageRangeUnresolvedRowHints.clear( );
		Iterator<String> iter = keys.iterator( );
		while(iter.hasNext( ))
		{
			String key = iter.next( );
			UnresolvedRowHint hint = docRangeUnresolvedRowHints.get( key );
			if ( hint != null )
			{
				pageRangeUnresolvedRowHints.add( hint );
			}
		}
	}
	
	ArrayList pageRangeUnresolvedRowHints = new ArrayList();
	public List<UnresolvedRowHint> getUnresolvedRowHints( )
	{
		return pageRangeUnresolvedRowHints;
	}
	
	protected ArrayList columnHints = new ArrayList( );

	public List getTableColumnHints( )
	{
		return columnHints;
	}

	public void addTableColumnHints( List hints )
	{
		columnHints.addAll( hints );
	}
	
	public void addTableColumnHint(TableColumnHint hint)
	{
		columnHints.add( hint );
	}

	public UnresolvedRowHint getUnresolvedRowHint( String key )
	{
		if ( docRangeUnresolvedRowHints.size( ) > 0 )
		{
			return docRangeUnresolvedRowHints.get( key );
		}
		return null;
	}

	public void addUnresolvedRowHint(String key,  UnresolvedRowHint hint )
	{
		currentPageUnresolvedRowHints.put( key, hint );
	}

	public void clearPageHint( )
	{
		columnHints.clear( );
		pageHints.clear( );
		
	}
	
	public void resetRowHint()
	{
		if ( !context.emptyPage )
		{
			docRangeUnresolvedRowHints.clear( );
			docRangeUnresolvedRowHints.putAll( currentPageUnresolvedRowHints );
			currentPageUnresolvedRowHints.clear( );
		}
	}
	
	public void setLayoutPageHint( IPageHint pageHint )
	{
		if ( pageHint != null )
		{
			context.pageNumber =  pageHint.getPageNumber( );
			context.masterPage = pageHint.getMasterPage( );
			// column hints
			int count = pageHint.getTableColumnHintCount( );
			for ( int i = 0; i < count; i++ )
			{
				columnHints.add( pageHint.getTableColumnHint( i ) );
			}
			// unresolved row hints
			count = pageHint.getUnresolvedRowCount( );
			if(count>0)
			{
				for ( int i = 0; i < count; i++ )
				{
					UnresolvedRowHint hint = pageHint.getUnresolvedRowHint( i );
					String key = getHintMapKey(hint.getTableId( ));
					docRangeUnresolvedRowHints.put( key, hint );
				}
			}
//			// size based page break hints
//			for ( int i = 0; i < pageHint.getSectionCount( ); i++ )
//			{
//				PageSection section = pageHint.getSection( i );
//				if ( section instanceof SizeBasedPageSection )
//				{
//					SizeBasedPageSection sizeBasedSection = (SizeBasedPageSection)section;
//					sizeBasedPageBreakHints.put( section.startOffset, sizeBasedSection.start );
//					sizeBasedPageBreakHints.put( section.endOffset, sizeBasedSection.end );
//				}
//			}
		}
	}
	
	public String getHintMapKey(String tableId)
	{
		String key = tableId;
		List hints = getTableColumnHint( key );
		Iterator iter = hints.iterator( );
		while(iter.hasNext( ))
		{
			int[] vs = (int[])iter.next( );
			key = key +"-" + vs[0] + "-" + vs[1];
		}
		return key;
	}
	
	public List getTableColumnHint( String tableId )
	{
		List list = new ArrayList();
		if ( columnHints.size( ) > 0 )
		{
			Iterator iter = columnHints.iterator( );
			while ( iter.hasNext( ) )
			{
				TableColumnHint hint = (TableColumnHint) iter.next( );
				if ( tableId.equals( hint.getTableId( ) ) )
				{
					list.add( new int[]{hint.getStart( ),
							hint.getStart( ) + hint.getColumnCount( )} );
				}
			}
		}
		return list;
	}

}