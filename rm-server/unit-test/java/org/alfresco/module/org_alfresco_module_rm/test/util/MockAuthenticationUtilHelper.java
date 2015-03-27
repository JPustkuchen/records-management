/*
 * Copyright (C) 2005-2015 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */

package org.alfresco.module.org_alfresco_module_rm.test.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import org.alfresco.module.org_alfresco_module_rm.util.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * A helper to create or initialise mock {@link AuthenticationUtil}s.
 * 
 * @author tpage
 */
public class MockAuthenticationUtilHelper
{
    /**
     * Create a Mockito mock <code>AuthenticationUtil</code> that executes all methods assuming the user has permission.
     * If the mock is asked for details about the user then it assumes the user is "admin".
     * 
     * @return The new mock.
     */
    public static AuthenticationUtil create()
    {
        AuthenticationUtil mockAuthenticationUtil = mock(AuthenticationUtil.class);

        setup(mockAuthenticationUtil);

        return mockAuthenticationUtil;
    }

    /**
     * Set up a Mockito mock <code>AuthenticationUtil</code> so that it executes all methods assuming the user has
     * permissions. If the mock is asked for details about the user then it assumes the user is "admin".
     * <p>
     * TODO: Change this method to private and this class to be a factory.
     * 
     * @param mockAuthenticationUtil The mock to initialise.
     */
    @SuppressWarnings("unchecked")
    protected static void setup(AuthenticationUtil mockAuthenticationUtil)
    {
        reset(mockAuthenticationUtil);

        // just do the work
        doAnswer(new Answer<Object>()
        {
            @SuppressWarnings("rawtypes")
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                RunAsWork work = (RunAsWork) invocation.getArguments()[0];
                return work.doWork();
            }

        }).when(mockAuthenticationUtil).<Object> runAsSystem(any(RunAsWork.class));

        // just do the work
        doAnswer(new Answer<Object>()
        {
            @SuppressWarnings("rawtypes")
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                RunAsWork work = (RunAsWork) invocation.getArguments()[0];
                return work.doWork();
            }

        }).when(mockAuthenticationUtil).<Object> runAs(any(RunAsWork.class), anyString());

        // assume admin
        doReturn("admin").when(mockAuthenticationUtil).getAdminUserName();
        doReturn("admin").when(mockAuthenticationUtil).getFullyAuthenticatedUser();
    }
}
