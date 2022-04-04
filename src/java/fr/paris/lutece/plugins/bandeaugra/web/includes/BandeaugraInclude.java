/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.bandeaugra.web.includes;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.service.content.PageData;
import fr.paris.lutece.portal.service.includes.PageInclude;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

/**
 * Page include to add the
 */
public class BandeaugraInclude implements PageInclude
{

    private static final String TEMPLATE_INCLUDE_BANDEAU_GRA = "/skin/plugins/bandeaugra/include_bandeaugra.html";

    // Marks
    public static final String MARK_BANDEAU_INCLUDE = "bandeaugra_include";
    private static final String MARK_URL_BANDEAU = "url_bandeau";
    private static final String MARK_IS_USER_AUTHENTICATED = "is_user_authenticated";

    // Properties
    private static final String PROPERTY_URL_BANDEAU = "bandeaugra.includeUrl.webappBandeau";

    // Services

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillTemplate( Map<String, Object> rootModel, PageData data, int nMode, HttpServletRequest request )
    {

        if ( request != null )
        {

            String strUrlBandeau = AppPropertiesService.getProperty( PROPERTY_URL_BANDEAU );
            HashMap<String, Object> mapInclude = new HashMap<>( );

            LuteceUser user = null;
            try
            {
                user = SecurityService.getInstance( ).getRemoteUser( request );

            }
            catch( UserNotSignedException e )
            {
            	AppLogService.error( e.getMessage( ), e );
            }

            mapInclude.put( MARK_IS_USER_AUTHENTICATED, user != null );
            mapInclude.put( MARK_URL_BANDEAU, strUrlBandeau );

            HtmlTemplate templateInclude = AppTemplateService.getTemplate( TEMPLATE_INCLUDE_BANDEAU_GRA, request.getLocale( ), mapInclude );

            rootModel.put( MARK_BANDEAU_INCLUDE, templateInclude.getHtml( ) );
        }
    }
}
