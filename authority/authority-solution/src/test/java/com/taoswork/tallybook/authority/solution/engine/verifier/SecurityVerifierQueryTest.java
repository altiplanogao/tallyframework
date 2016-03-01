package com.taoswork.tallybook.authority.solution.engine.verifier;

import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.KAccessibleScope;
import com.taoswork.tallybook.authority.core.verifier.IKAccessVerifier;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.authority.solution.mockup.domain.resource.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierQueryTest extends VerifierTestSupport{

    //    private final String resourceEntry = TypesEnums.DOC;
//    private final PermissionDataMockuper mocker = new PermissionDataMockuper(resourceEntry, Access.Read);
//
//    private final ISimpleKAuthority auth4A = mocker.authorityWith(true, true, false, false, false);
//    private final ISimpleKAuthority auth4B = mocker.authorityWith(true, false, true, false, false);
//    private final ISimpleKAuthority auth4C = mocker.authorityWith(true, false, false, true, false);
//    private final ISimpleKAuthority auth4D = mocker.authorityWith(true, false, false, false, true);
//
    private static final String TENANT = "test-tenant";

    @BeforeClass
    public static void beforeClass(){
        setupDatabaseData();
        makeDatabaseTestData(TENANT);
    }

    @AfterClass
    public static void afterClass(){
        teardownDatabaseData();
    }

    @Test
    public void testQueryWithoutCases() {
        Class<CCFile> resourceClz = CCFile.class;
        XFileRepo<CCFile> XFileRepo = new XFileRepo<CCFile>(entityService.getDatastore(), resourceClz);
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = Protection.unifiedResourceName(resourceClz);
        IKProtectionCenter protectionCenter = permissionEngine.getProtectionCenter(mockuper.PROTECTION_SPACE, TENANT);

        for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            IKProtection protection = protectionCenter.getProtection(resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        for (IKAuthority user : new IKAuthority[]{user_G____, user_GAB__, user_GABCD}) {
            {
                KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
                IKProtection protection = protectionCenter.getProtection(resourceEntry);
                List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
                queryLikeChecker(results)
                        .check(file_G_____, true)
                        .check(file__A____, true)
                        .check(file____C__, true)
                        .check(file__AB___, true)
                        .check(file__A_C__, true)
                        .check(file____CD_, true)
                        .check(file__ABC__, true)
                        .check(file__A_CD_, true)
                        .check(file__ABCD_, true)
                        .check(file______E, true)
                        .check(file__ABCDE, true)
                        .ensureFullyChecked();
            }
        }
    }

    @Test
    public void testQuery_MasterControl_FitAll() {
        Class<CM1File> resourceClz = CM1File.class;
        XFileRepo<CM1File> XFileRepo = new XFileRepo<CM1File>(entityService.getDatastore(), resourceClz);
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = Protection.unifiedResourceName(resourceClz);
        IKProtectionCenter protectionCenter = permissionEngine.getProtectionCenter(mockuper.PROTECTION_SPACE, TENANT);

        for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            IKProtection protection = protectionCenter.getProtection(resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_G____;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            IKProtection protection = protectionCenter.getProtection(resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GAB__;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            IKProtection protection = protectionCenter.getProtection(resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            IKProtection protection = protectionCenter.getProtection(resourceEntry);
            List<CM1File> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testQuery_MasterControl_FitAny() {
        Class<CM0File> resourceClz = CM0File.class;
        XFileRepo<CM0File> XFileRepo = new XFileRepo<CM0File>(entityService.getDatastore(), resourceClz);
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = Protection.unifiedResourceName(resourceClz);
        IKProtectionCenter protectionCenter = permissionEngine.getProtectionCenter(mockuper.PROTECTION_SPACE, TENANT);

        for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_G____;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GAB__;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, false)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testQuery_SelfControl_FitAll() {
        Class<CS1File> resourceClz = CS1File.class;
        XFileRepo<CS1File> XFileRepo = new XFileRepo<CS1File>(entityService.getDatastore(), resourceClz);
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = Protection.unifiedResourceName(resourceClz);
        IKProtectionCenter protectionCenter = permissionEngine.getProtectionCenter(mockuper.PROTECTION_SPACE, TENANT);

        {
            IKAuthority user = user_N____;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user__AB__;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user__ABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, false)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_G____;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GAB__;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testQuery_SelfControl_FitAny() {
        Class<CS0File> resourceClz = CS0File.class;
        XFileRepo<CS0File> XFileRepo = new XFileRepo<CS0File>(entityService.getDatastore(), resourceClz);
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = Protection.unifiedResourceName(resourceClz);
        IKProtectionCenter protectionCenter = permissionEngine.getProtectionCenter(mockuper.PROTECTION_SPACE, TENANT);

        {
            IKAuthority user = user_N____;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user__AB__;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, false)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, false)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user__ABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, false)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_G____;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GAB__;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, false)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = user_GABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mockuper.normalAccess, resourceEntry);
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    private QueryLikeChecker queryLikeChecker(List<?> results) {
        return new QueryLikeChecker(results);
    }

    class QueryLikeChecker {
        private List<?> results;
        private int checked = 0;

        public QueryLikeChecker(List<?> results) {
            this.results = results;
        }

        public QueryLikeChecker check(Serializable instance, boolean exist) {
            XFile doc = (XFile) instance;
            if (exist) {
                Assert.assertTrue(results.contains(doc));
            } else {
                Assert.assertFalse(results.contains(doc));
            }
            checked++;
            return this;
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(docCount, checked);
        }
    }


}
