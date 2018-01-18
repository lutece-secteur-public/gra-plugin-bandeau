/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.bandeaugra.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.xpage.MVCApplication;
import fr.paris.lutece.portal.util.mvc.xpage.annotations.Controller;
import fr.paris.lutece.portal.web.constants.Markers;
import fr.paris.lutece.portal.web.xpages.XPage;

/**
 * This class provides a simple implementation of an XPage
 */
@Controller( xpageName = "bandeaugra", pageTitleI18nKey = "bandeaugra.xpage.bandeaugra.pageTitle", pagePathI18nKey = "bandeaugra.xpage.bandeaugra.pagePathLabel" )
public class BandeaugraApp extends MVCApplication
{
    private static final String TEMPLATE_XPAGE = "/skin/plugins/bandeaugra/bandeaugra.html";
    private static final String VIEW_AUTH = "auth";
    private static final String PARAMETER_BACK_URL = "back_url";
    private static final String MARK_BACK_URL = "back_url";

    /**
     * Returns the content of the page bandeaugra.
     * 
     * @param request
     *            The HTTP request
     * @return The view
     */
    @View( value = VIEW_AUTH, defaultView = true )
    public XPage viewAuth( HttpServletRequest request ) throws UserNotSignedException
    {

        LuteceUser luteceUser = SecurityService.getInstance( ).getRegisteredUser( request );

        if ( luteceUser == null )
        {
            throw new UserNotSignedException( );
        }

        String strBackUrl = getBackUrl( request );

        Map<String, Object> model = getModel( );
        model.put( Markers.BASE_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_BACK_URL, strBackUrl );

        XPage xpage = getXPage( TEMPLATE_XPAGE, request.getLocale( ), model );
        xpage.setStandalone( true );
        return xpage;

    }

    private static String getBackUrl( HttpServletRequest request )
    {
        return request.getParameter( PARAMETER_BACK_URL );
    }
}
