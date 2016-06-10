/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.utils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Mikle Garin
 */

public class CoreSwingUtils
{
    /**
     * Returns window ancestor for specified component or null if it doesn't exist.
     *
     * @param component component to process
     * @return window ancestor for specified component or null if it doesn't exist
     */
    public static Window getWindowAncestor ( final Component component )
    {
        if ( component == null )
        {
            return null;
        }
        if ( component instanceof Window )
        {
            return ( Window ) component;
        }
        for ( Container p = component.getParent (); p != null; p = p.getParent () )
        {
            if ( p instanceof Window )
            {
                return ( Window ) p;
            }
        }
        return null;
    }

    /**
     * Returns root pane for the specified component or null if it doesn't exist.
     *
     * @param component component to look under
     * @return root pane for the specified component or null if it doesn't exist
     */
    public static JRootPane getRootPane ( final Component component )
    {
        if ( component == null )
        {
            return null;
        }
        else if ( component instanceof JFrame )
        {
            return ( ( JFrame ) component ).getRootPane ();
        }
        else if ( component instanceof JDialog )
        {
            return ( ( JDialog ) component ).getRootPane ();
        }
        else if ( component instanceof JWindow )
        {
            return ( ( JWindow ) component ).getRootPane ();
        }
        else if ( component instanceof JApplet )
        {
            return ( ( JApplet ) component ).getRootPane ();
        }
        else if ( component instanceof JRootPane )
        {
            return ( JRootPane ) component;
        }
        else
        {
            return getRootPane ( component.getParent () );
        }
    }

    /**
     * Returns whether or not component is currently added to a displayable window and visible.
     *
     * @param component component to process
     * @return true if component is currently added to a displayable window and visible, false otherwise
     */
    public static boolean isVisibleOnScreen ( final Component component )
    {
        final Window window = getWindowAncestor ( component );
        return window != null && window.isDisplayable () && component.isVisible ();
    }

    /**
     * Returns component bounds on screen.
     *
     * @param component component to process
     * @return component bounds on screen
     */
    public static Rectangle getBoundsOnScreen ( final Component component )
    {
        return new Rectangle ( component.getLocationOnScreen (), component.getSize () );
    }

    /**
     * Returns component bounds inside its window.
     * This will return component bounds relative to window root pane location, not the window location.
     *
     * @param component component to process
     * @return component bounds inside its window
     */
    public static Rectangle getBoundsInWindow ( final Component component )
    {
        return component instanceof Window || component instanceof JApplet ? getRootPane ( component ).getBounds () :
                getRelativeBounds ( component, getRootPane ( component ) );
    }

    /**
     * Returns component bounds relative to another component.
     *
     * @param component  component to process
     * @param relativeTo component relative to which bounds will be returned
     * @return component bounds relative to another component
     */
    public static Rectangle getRelativeBounds ( final Component component, final Component relativeTo )
    {
        return new Rectangle ( getRelativeLocation ( component, relativeTo ), component.getSize () );
    }

    /**
     * Returns component location relative to another component.
     *
     * @param component  component to process
     * @param relativeTo component relative to which location will be returned
     * @return component location relative to another component
     */
    public static Point getRelativeLocation ( final Component component, final Component relativeTo )
    {
        final Point los = component.getLocationOnScreen ();
        final Point rt = relativeTo.getLocationOnScreen ();
        return new Point ( los.x - rt.x, los.y - rt.y );
    }

    /**
     * Returns whether specified components have the same ancestor or not.
     *
     * @param component1 first component
     * @param component2 second component
     * @return true if specified components have the same ancestor, false otherwise
     */
    public static boolean isSameAncestor ( final Component component1, final Component component2 )
    {
        return getWindowAncestor ( component1 ) == getWindowAncestor ( component2 );
    }

    /**
     * Returns window decoration insets.
     *
     * @param window window to retrieve insets for
     * @return window decoration insets
     */
    public static Insets getWindowDecorationInsets ( final Window window )
    {
        final Insets insets = new Insets ( 0, 0, 0, 0 );
        if ( window instanceof Dialog || window instanceof Frame )
        {
            final JRootPane rootPane = CoreSwingUtils.getRootPane ( window );
            if ( rootPane != null )
            {
                if ( window.isShowing () )
                {
                    if ( window instanceof Dialog && !( ( Dialog ) window ).isUndecorated () ||
                            window instanceof Frame && !( ( Frame ) window ).isUndecorated () )
                    {
                        // Calculating exact decoration insets based on root pane insets
                        final Rectangle wlos = CoreSwingUtils.getBoundsOnScreen ( window );
                        final Rectangle rlos = CoreSwingUtils.getBoundsOnScreen ( rootPane );
                        insets.top = rlos.y - wlos.y;
                        insets.left = rlos.x - wlos.x;
                        insets.bottom = wlos.y + wlos.height - rlos.y - rlos.height;
                        insets.right = wlos.x + wlos.width - rlos.x - rlos.width;
                    }
                    else
                    {
                        // Fallback for custom window decorations
                        // Usually 25px should be around average decoration header width
                        insets.top = 25;
                    }
                }
                else
                {
                    // Fallback for non-displayed window
                    // Usually 25px should be around average decoration header width
                    insets.top = 25;
                }
            }
        }
        return insets;
    }

    /**
     * Returns whether or not specified component is placed on a fullscreen window.
     *
     * @param component component to process
     * @return true if specified component is placed on a fullscreen window, false otherwise
     */
    public static boolean isFullScreen ( final Component component )
    {
        final Window window = getWindowAncestor ( component );
        if ( window != null )
        {
            final GraphicsConfiguration gc = window.getGraphicsConfiguration ();
            if ( gc != null )
            {
                final GraphicsDevice device = gc.getDevice ();
                return device != null && device.getFullScreenWindow () == window;
            }
        }
        return false;
    }

    /**
     * Returns mouse point relative to specified component.
     *
     * @param component component to process
     * @return mouse point relative to specified component
     */
    public static Point getMousePoint ( final Component component )
    {
        final Point p = MouseInfo.getPointerInfo ().getLocation ();
        final Point los = component.getLocationOnScreen ();
        return new Point ( p.x - los.x, p.y - los.y );
    }

    /**
     * Will invoke the specified action later in EDT in case it is called from non-EDT thread.
     * Otherwise action will be performed immediately.
     *
     * @param runnable runnable
     */
    public static void invokeLater ( final Runnable runnable )
    {
        SwingUtilities.invokeLater ( runnable );
    }

    /**
     * Will invoke the specified action in EDT in case it is called from non-EDT thread.
     *
     * @param runnable runnable
     * @throws InterruptedException      if we're interrupted while waiting for the EDT to finish excecuting {@code doRun.run()}
     * @throws InvocationTargetException if an exception is thrown while running {@code doRun}
     */
    public static void invokeAndWait ( final Runnable runnable ) throws InterruptedException, InvocationTargetException
    {
        if ( SwingUtilities.isEventDispatchThread () )
        {
            runnable.run ();
        }
        else
        {
            SwingUtilities.invokeAndWait ( runnable );
        }
    }

    /**
     * Will invoke the specified action in EDT in case it is called from non-EDT thread.
     * It will also block any exceptions thrown by "invokeAndWait" method.
     *
     * @param runnable runnable
     */
    public static void invokeAndWaitSafely ( final Runnable runnable )
    {
        try
        {
            invokeAndWait ( runnable );
        }
        catch ( final Throwable e )
        {
            //
        }
    }
}