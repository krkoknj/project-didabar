import React from "react";
import { Routes, Route } from "react-router-dom";
import DashBoard from "./pages/DashBoard";
import Home from "./pages/Home";
import Join from "./pages/Join";
import KakaoLogin from "./pages/KakaoLogin";
import EmailAuth from "./pages/EmailAuth";
import { useRecoilState, useRecoilValue } from "recoil";
import { AnimatePresence } from "framer-motion";
import { didabaraState, loginState, userState } from "./config/Atom";
import Mypage from "./pages/Mypage";
import PersonalInfo from "./pages/PersonalInfo";
import NavigationBar from "./components/NavigationBar";
import MypageMain from "./pages/MypageMain";
import FindInfo from "./pages/FindInfo";
import SubscriptionMain from "./pages/SubscriptionMain";
import Loginform from "./components/Loginform";
import CreateModal from "./components/CreateModal";
import AvatarPickerModal from "./components/AvatarPickerModal";
import { useQuery } from "react-query";
import { getUserData } from "./config/APIs";
import { getDidabara } from "./config/APIs";
import DocumentList from "./components/DocumentList";
import ViewContainer from "./components/ViewContainer";
import DeleteAccount from "./pages/DeleteAccount";
import UploadedDocs from "./pages/UploadedDocs";
import SubscriptionList from "./components/SubscriptionList";

function Router() {
  const isLogin = useRecoilValue(loginState);
  const [user, setUser] = useRecoilState(userState);
  const [didabara, setDidabara] = useRecoilState(didabaraState);

  const { isLoading } = useQuery("userData", getUserData, {
    refetchOnWindowFocus: false,
    retry: 0,
    onSuccess: (data) => setUser(data),
  });

  const { isLoading2 } = useQuery("didabara", getDidabara, {
    refetchOnWindowFocus: false,
    retry: false,
    onSuccess: (data) => {
      setDidabara((prev) => {
        return { ...prev, create: data.data };
      });
    },
  });

  return (
    <>
      {isLoading ? null : (
        <>
          {" "}
          <NavigationBar />
          <AnimatePresence>{isLogin ? <Loginform /> : null}</AnimatePresence>
          <Routes>
            {/* {!user && (
          <> */}
            <Route path="/kakaologin" element={<KakaoLogin />} />
            <Route path="/join" element={<Join />} />
            <Route path="/emailconfig/:username" element={<EmailAuth />} />
            <Route path="/find-info" element={<FindInfo />} />
            {/* </>
        )}
        {user && (
          <> */}
            <Route path="/dashboard" element={<DashBoard />}>
              <Route
                path="/dashboard/myboard/:document"
                element={<DocumentList />}
              />
              <Route path="/dashboard/myboard/" element={<DocumentList />} />
              <Route
                path="/dashboard/publicboard/:document"
                element={<SubscriptionList />}
              />
              <Route
                path="/dashboard/publicboard"
                element={<SubscriptionList />}
              />
            </Route>
            <Route path="/dashboard/pages/:docId" element={<ViewContainer />} />
            <Route path="/dashboard/create" element={<CreateModal />} />

            {isLoading2 ? null : (
              <Route path="/mypage" element={<Mypage />}>
                <Route path="main" element={<MypageMain />} />
                <Route path="personal-info" element={<PersonalInfo />} />
                <Route path="updateimage" element={<AvatarPickerModal />} />
                <Route path="category-lists" element={<SubscriptionMain />} />
                <Route path="uploaded-docs" element={<UploadedDocs />} />
              </Route>
            )}
            {/* </>
        )} */}
            <Route path="/" element={<Home />} />
            <Route path="/deleted" element={<DeleteAccount />} />
          </Routes>
        </>
      )}
    </>
  );
}

export default Router;
