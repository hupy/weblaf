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

package com.alee.managers.icon;

/**
 * Class referencing all icons used within WebLaF library UI elements.
 * These icons are only loaded into memory on demand so it is safe to reference them.
 *
 * @author Mikle Garin
 */

public final class Icons
{
    /**
     * General purpose icons.
     */
    public static final LazyIcon pin = new LazyIcon ( "pin", 16, 16 );
    public static final LazyIcon pinHover = new LazyIcon ( "pin-hover", 16, 16 );
    public static final LazyIcon pinSelected = new LazyIcon ( "pin-selected", 16, 16 );
    public static final LazyIcon pinSelectedHover = new LazyIcon ( "pin-hover", 16, 16 );
    public static final LazyIcon external = new LazyIcon ( "external", 16, 16 );
    public static final LazyIcon externalHover = new LazyIcon ( "external-hover", 16, 16 );
    public static final LazyIcon maximize = new LazyIcon ( "maximize", 16, 16 );
    public static final LazyIcon maximizeHover = new LazyIcon ( "maximize-hover", 16, 16 );
    public static final LazyIcon shrink = new LazyIcon ( "shrink", 16, 16 );
    public static final LazyIcon shrinkHover = new LazyIcon ( "shrink-hover", 16, 16 );
    public static final LazyIcon cross = new LazyIcon ( "cross", 16, 16 );
    public static final LazyIcon crossHover = new LazyIcon ( "cross-hover", 16, 16 );
    public static final LazyIcon crossSmall = new LazyIcon ( "cross-small", 10, 10 );
    public static final LazyIcon crossSmallHover = new LazyIcon ( "cross-small-hover", 10, 10 );
}